package icu.xbg.dbrouter.datasource.builder.dynamic;

import icu.xbg.dbrouter.config.DBCommonConfig;
import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.datasource.DataSourceBuilderManager;
import icu.xbg.dbrouter.datasource.source.BaseDynamicDataSource;
import icu.xbg.dbrouter.datasource.DynamicDataSourceBuilder;
import icu.xbg.dbrouter.datasource.DynamicDataSourceFactory;
import icu.xbg.dbrouter.meta.MetaResolver;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 14:55
 */
@Getter
@Slf4j
public abstract class BaseDynamicDataSourceBuilder implements DynamicDataSourceBuilder {

    private final DBRouterProperties properties;

    private final DynamicDataSourceFactory factory;

    private final MetaResolver metaResolver;

    private final HashMap<Object, Object> dbMap;

    private final DataSourceBuilderManager dataSourceBuilderManager;

    public BaseDynamicDataSourceBuilder(DBRouterProperties properties, MetaResolver metaResolver, DynamicDataSourceFactory factory,DataSourceBuilderManager dataSourceBuilderManager) {
        this.properties = properties;
        this.factory = factory;
        this.metaResolver = metaResolver;
        this.dbMap = new HashMap<>();
        this.dataSourceBuilderManager = dataSourceBuilderManager;
    }

    @Override
    public BaseDynamicDataSource build() {
        BaseDynamicDataSource instance = factory.getInstance(metaResolver);
        instance.setTargetDataSources(getTargetDataSource());
        instance.setDefaultTargetDataSource(getDefaultDataSource());
        return instance;
    }


    protected Map<Object, Object> getTargetDataSource() {
        if (dbMap.isEmpty()){
            DBRouterProperties properties = getProperties();
            List<DBProperties> dbProperties = properties.getDbPool();
            for (DBProperties prop:dbProperties){
                dbMap.put(prop.getDbName(),buildSubDataSource(prop));
            }
        }
        return dbMap;
    }

    protected DataSource getDefaultDataSource() {
        DBRouterProperties properties = getProperties();
        DataSource primaryDB = null;
        DBProperties primaryDbProp = properties.getPrimaryDb();
        // 尝试获取主库配置,从库池获取
        if (Objects.nonNull(properties.getPrimaryDb())){
            // 配置获取

            DBProperties finalPrimaryDbProp = primaryDbProp;
            Optional<DBProperties> first = properties.getDbPool().stream().
                    filter(item -> item.getDbName().equals(finalPrimaryDbProp.getDbName()))
                    .findFirst();
            if (first.isEmpty()){
                // 校验
                if (ableToBuildPrimaryDataSource()){
                    primaryDbProp.setUrl(getPrimaryUrl());
                    primaryDbProp.setDriverClassName(getPrimaryClassDriverName());
                    primaryDbProp.setUser(getPrimaryUser());
                    primaryDbProp.setPassword(getPrimaryPassword());
                    primaryDB = buildPrimaryDataSource(primaryDbProp);
                }
            }else {
                primaryDB = (DataSource) dbMap.get(first.get().getDbName());
            }
        }else {
            List<DBProperties> dbPool = properties.getDbPool();
            if (Objects.isNull(dbPool)||dbPool.isEmpty()){
                log.error("The primary data source is not configured (possible cause: the dbName in the dbPool does not match the dbName of the primary data source, or the data source connection information is not configured in the primary data source)");
                throw new RuntimeException("can't config primary dataSource");
            }
            Optional<Map.Entry<DBProperties, Object>> firstOne = dbPool.stream().map(item->Map.entry(item,dbMap.get(item.getDbName())))
                    .filter(entry->Objects.nonNull(entry.getValue())).findFirst();

            if (firstOne.isPresent() && firstOne.get() instanceof DataSource firstDataSource){
                primaryDB = firstDataSource;
                primaryDbProp = firstOne.get().getKey();
                log.info("primary has been choose the dbPool's first dataSource, dbName :{}",primaryDbProp.getDbName());
            }

        }
        return primaryDB;
    }

    // 构造数据源
    protected abstract DataSource buildSubDataSource(DBProperties dbProperties);

    protected abstract DataSource buildPrimaryDataSource(DBProperties dbProperties);

    protected boolean ableToBuildPrimaryDataSource(){
        return StringUtils.hasLength(getPrimaryUrl()) && StringUtils.hasLength(getPrimaryPassword())
                && StringUtils.hasLength(getPrimaryClassDriverName()) && StringUtils.hasLength(getPrimaryUser());
    }

    protected String getPrimaryUrl(){
        DBProperties primaryDbConfig = getProperties().getPrimaryDb();
        return Objects.isNull(primaryDbConfig)?"": Optional.ofNullable(primaryDbConfig.getUrl()).orElse("");
    };

    protected String getPrimaryClassDriverName(){
        String driverName = "";
        DBCommonConfig commonConfig = getProperties().getCommonConfig();
        DBProperties primaryDbConfig = getProperties().getPrimaryDb();
        if (Objects.nonNull(commonConfig)){
            driverName = StringUtils.hasLength(commonConfig.getDriverClassName())?commonConfig.getDriverClassName():driverName;
        }
        if (Objects.nonNull(primaryDbConfig)){
            driverName = StringUtils.hasLength(primaryDbConfig.getDriverClassName())?primaryDbConfig.getDriverClassName():driverName;

        }
        return driverName;
    };

    protected String getPrimaryUser(){
        String primaryUser = "";
        DBCommonConfig commonConfig = getProperties().getCommonConfig();
        DBProperties primaryDbConfig = getProperties().getPrimaryDb();
        if (Objects.nonNull(commonConfig)){
            primaryUser = StringUtils.hasLength(commonConfig.getUser())?commonConfig.getUser():primaryUser;
        }
        if (Objects.nonNull(primaryDbConfig)){
            primaryUser = StringUtils.hasLength(primaryDbConfig.getUser())?primaryDbConfig.getUser():primaryUser;

        }
        return primaryUser;
    };

    protected String getPrimaryPassword(){
        String password = "";
        DBCommonConfig commonConfig = getProperties().getCommonConfig();
        DBProperties primaryDbConfig = getProperties().getPrimaryDb();
        if (Objects.nonNull(commonConfig)){
            password = StringUtils.hasLength(commonConfig.getPassword())?commonConfig.getPassword():password;
        }
        if (Objects.nonNull(primaryDbConfig)){
            password = StringUtils.hasLength(primaryDbConfig.getPassword())?primaryDbConfig.getPassword():password;

        }
        return password;
    };
}
