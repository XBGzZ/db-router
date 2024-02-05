package icu.xbg.dbrouter.datasource.builder.strategy;

import com.zaxxer.hikari.HikariDataSource;
import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.datasource.DataSourceBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *   做最简单的配置
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 20:05
 */
@Component
@ConditionalOnMissingBean(HikariCPDataSourceBuilder.class)
public class HikariCPDataSourceBuilder implements DataSourceBuilder{
    private static final String NAME = "HIKARICP";
    // 自动提交
    private static final Boolean AUTO_COMMIT = true;
    // 超时 默认 30000ms
    private static final Integer CONNECTION_TIMEOUT = 30000;
    // 空闲超时
    private static final Integer IDLE_TIMEOUT = 600000;
    // 保持活跃时间
    private static final Integer KEEPALIVE_TIME = 0;
    // 最大存活时间
    private static final Integer MAX_LIFETIME = 1800000;
    // 最小空闲数量
    private static final Integer MINIMUM_IDLE = 5;

    private static final String AUTO_COMMIT_KEY = "autoCommit";
    private static final String CONNECTION_TIMEOUT_KEY = "connectionTimeout";
    private static final String IDLE_TIMEOUT_KEY = "idleTimeout";
    private static final String KEEPALIVE_TIME_KEY = "keepaliveTime";
    private static final String MAX_LIFETIME_KEY = "maxLifetime";
    private static final String MINIMUM_IDLE_KEY = "minimumIdle";


    @Override
    public DataSource buildDataSource(DBProperties properties) {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(properties.getUrl());
        hikariDataSource.setUsername(properties.getUser());
        hikariDataSource.setPassword(properties.getPassword());
        hikariDataSource.setDriverClassName(properties.getDriverClassName());
        return hikariDataSource;
    }

    @Override
    public String getBuilderName() {
        return NAME;
    }

    protected void loadPoolConfig(HikariDataSource dataSource,DBProperties properties){
        Map<String, Object> poolDetailConfig = properties.getPoolDetailConfig();
        dataSource.setAutoCommit((Boolean) poolDetailConfig.getOrDefault(AUTO_COMMIT_KEY, AUTO_COMMIT));
        dataSource.setConnectionTimeout((Integer) poolDetailConfig.getOrDefault(CONNECTION_TIMEOUT_KEY, CONNECTION_TIMEOUT));
        dataSource.setIdleTimeout((Integer) poolDetailConfig.getOrDefault(IDLE_TIMEOUT_KEY, IDLE_TIMEOUT));
        dataSource.setKeepaliveTime((Integer) poolDetailConfig.getOrDefault(KEEPALIVE_TIME_KEY, KEEPALIVE_TIME));
        dataSource.setMaxLifetime((Integer) poolDetailConfig.getOrDefault(MAX_LIFETIME_KEY, MAX_LIFETIME));
        dataSource.setMinimumIdle((Integer) poolDetailConfig.getOrDefault(MINIMUM_IDLE_KEY, MINIMUM_IDLE));
    }

}
