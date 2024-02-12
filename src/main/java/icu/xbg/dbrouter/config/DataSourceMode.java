package icu.xbg.dbrouter.config;

import icu.xbg.dbrouter.datasource.builder.dynamic.SimpleDynamicDataSourceBuilder;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  数据库连接配置，采用连接池？简单连接？还是自定义
 *  自定义需要使用者手动装配
 *
 *  pool   模式:以poolBuilderName为准
 *             默认 HikariCP
 *  single 模式:默认采用Spring
 *             的SingleConnectionDataSource
 *  custom 模式:需要指定poolBuilderClass
 *             或 poolBuilderName
 *  具体实现参考:
 *  {@link SimpleDynamicDataSourceBuilder}
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 16:06
 */
public enum DataSourceMode {
    // 连接池
    POOL,
    // 简单连接，
    SINGLE,
    // 自定义策略
    CUSTOM
}
