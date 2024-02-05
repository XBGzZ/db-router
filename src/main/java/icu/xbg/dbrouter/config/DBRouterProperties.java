package icu.xbg.dbrouter.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *      Router功能的核心配置类
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 16:04
 */
@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "spring.datasource.db-router")
public class DBRouterProperties {

    @NestedConfigurationProperty
    private DBCommonConfig commonConfig;

    @NestedConfigurationProperty
    private DBProperties primaryDb;

    private Boolean hashCheck = false;

    // 数据库类型，连接池 简单连接 自定义连接
    private DataSourceMode type = DataSourceMode.POOL;

    // 详细配置
    private List<DBProperties> dbPool;
}
