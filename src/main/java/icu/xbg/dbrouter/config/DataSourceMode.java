package icu.xbg.dbrouter.config;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  数据库连接配置，采用连接池？简单连接？还是自定义
 *  自定义需要使用者手动装配
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 16:06
 */
public enum DataSourceMode {
    POOL,
    SINGLE,
    CUSTOM
}
