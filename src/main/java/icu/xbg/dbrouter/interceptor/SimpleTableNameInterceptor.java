package icu.xbg.dbrouter.interceptor;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.meta.MetaResolver;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *    利用表名的别名实现表的修改
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


    /* 前缀关键词 */
    public static final String JOIN = "JOIN";
    public static final String FROM = "FROM";
    public static final String UPDATE = "UPDATE";
    public static final String INTO = "INTO";

    /* 后缀关键词 */

    public static final String WHERE = "WHERE";

    public static final String ON = "ON";

    public static final String HAVING = "HAVING";

    public static final String GROUP = "GROUP";

    public static final String ORDER = "ORDER";

    public static final String SET = "SET";

    public SimpleTableNameInterceptor(Connection connection, DBProperties properties, MetaResolver resolver) {
        super(connection, properties, resolver);
    }

    @Override
    protected String modifyTableName(String sql, Integer tbCount, String dbName, String[] tbKeys, String suffix) {
        List<String> tbName = Arrays.stream(tbKeys).map(String::toLowerCase).toList();
        StringBuilder stringBuilder = new StringBuilder();
        if (tbCount > 1 && StringUtils.hasLength(suffix) && !tbName.isEmpty()) {
            String[] sqlFragments = sql.split(" ");
            boolean flag = false;
            for (int i = 0; i < sqlFragments.length; i++) {
                String fragment = sqlFragments[i];
                // 关键词匹配，判断是否开启复杂匹配
                if (matchKeywords(fragment)) {
                    flag = true;
                } else if (matchEndKeywords(fragment)){
                    flag = false;
                }
                if (flag && tbName.contains(fragment.toLowerCase())) {

                    if (i+1>=sqlFragments.length||matchEndKeywords(sqlFragments[i+1])){
                        // 终止，说明没有起别名，那么将原本的表名作为别名
                        fragment = getReplacement(fragment,suffix) + " " + fragment;
                    }else {
                        // 未终止，说明已经起了别名，因此不需要过多修改
                        fragment = getReplacement(fragment,suffix);
                    }
                }
                stringBuilder.append(fragment);
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    protected String getReplacement(String tableName, String suffix) {
        return tableName + "_" + suffix;
    }


    private boolean matchKeywords(String fragment) {
        return fragment.equalsIgnoreCase(FROM) || fragment.equalsIgnoreCase(INTO)
                || fragment.equalsIgnoreCase(JOIN) || fragment.equalsIgnoreCase(UPDATE);
    }

    private boolean matchEndKeywords(String fragment) {
        return fragment.equalsIgnoreCase(WHERE) || // where 查询时的终止
                fragment.equalsIgnoreCase(ON) || // on 是关联表时的终止
                fragment.equalsIgnoreCase(SET) ||
                fragment.equalsIgnoreCase(HAVING) || // Having 说明终止
                fragment.equalsIgnoreCase(GROUP) || // group by 终止
                fragment.equalsIgnoreCase(ORDER) || // order by 说明终止
                fragment.equalsIgnoreCase(",")|| // 下一个是 , 说明结束（老sql语法）
                fragment.contains(",") // 避免未分离的情况
                ;
    }

}
