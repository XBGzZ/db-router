package icu.xbg.dbrouter.datasource.builder.strategy;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.datasource.DataSourceBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 20:01
 */
@Component
@ConditionalOnMissingBean(SingleConnectDataSourceBuilder.class)
public class SingleConnectDataSourceBuilder implements DataSourceBuilder {
    private static final String NAME = "SINGLE";

    @Override
    public DataSource buildDataSource(DBProperties properties) {
        SingleConnectionDataSource singleConnectionDataSource = new SingleConnectionDataSource();
        singleConnectionDataSource.setUrl(properties.getUrl());
        singleConnectionDataSource.setUsername(properties.getUser());
        singleConnectionDataSource.setPassword(properties.getPassword());
        singleConnectionDataSource.setDriverClassName(properties.getDriverClassName());
        return singleConnectionDataSource;
    }

    @Override
    public String getBuilderName() {
        return NAME;
    }
}
