package icu.xbg.dbrouter.interceptor;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.meta.MetaResolver;
import icu.xbg.dbrouter.strategy.StrategyCache;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *    最简单的拦截，虽然简单高效
 *    但是存在bug
 *    需要避免 字段和表重名
 *    select count(*) tb from tb t
 *
 *
 *    如果需要自定义表拦截策略，请注入
 * {@link TableInterceptorBuilder}
 * </pre>
 *
 * @author XBG
 * @date 2024-02-04 13:48
 */
@Getter
@Setter
public class SimpleTableNameInterceptor extends ConnectProxy {

    public static final String PATTERN = "\\b%s\\b";
    public static final String JOIN_PATTERN = "\\bJOIN\\s+%s\\b";
    public static final String FROM_PATTERN = "\\bFROM\\s+%s\\b";


    public SimpleTableNameInterceptor(Connection connection, DBProperties properties, MetaResolver resolver) {
        super(connection, properties, resolver);
    }

    @Override
    protected String modifyTableName(String sql, Integer tbCount, String dbName, String[] tbKeys, String suffix) {
        String result = sql;
        if (tbCount > 1 && StringUtils.hasLength(suffix) && Objects.nonNull(tbKeys)) {
            for (String tableName : tbKeys) {
                String pattern = String.format(PATTERN, tableName); // 普通表名替换
                String joinPattern = String.format(JOIN_PATTERN, tableName); // 匹配 JOIN 子句中的表名
                String fromPattern = String.format(FROM_PATTERN, tableName); // 匹配 FROM 子句中的表名

                String replacement = getReplacement(tableName, suffix);
                // 替换表名
                result = result.replaceAll(pattern, replacement);
                result = result.replaceAll(joinPattern, "JOIN " + replacement);
                result = result.replaceAll(fromPattern, "FROM " + replacement);
            }

        }
        return result;
    }

    protected String getReplacement(String tableName, String suffix) {
        return tableName + "_" + suffix;
    }

}
