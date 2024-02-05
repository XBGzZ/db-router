package icu.xbg.dbrouter.config;

import icu.xbg.dbrouter.datasource.DataSourceBuilder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *      数据库配置类
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 16:04
 */
@Setter
@Getter
public class DBProperties {
    // 数据库名称
    private String dbName;
    // url地址
    private String url;
    // 用户名
    private String user;
    // 密码
    private String password;
    // 子表数
    private Integer tbCount=1;
    // 驱动
    private String driverClassName;
    // 数据源模式
    private DataSourceMode mode = DataSourceMode.POOL;

    // 只有连接池模式才会考虑下面的参数
    private String poolBuilderName;
    // 连接池的类
    private Class<? extends DataSourceBuilder> poolBuilderClass;
    // 特殊配置，需要使用特定的类进行配置
    private Map<String,Object> poolDetailConfig;

}
