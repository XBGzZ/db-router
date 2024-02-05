package icu.xbg.dbrouter.datasource.builder.dynamic;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.config.DataSourceMode;
import icu.xbg.dbrouter.datasource.DataSourceBuilder;
import icu.xbg.dbrouter.datasource.DataSourceBuilderManager;
import icu.xbg.dbrouter.datasource.DynamicDataSourceFactory;
import icu.xbg.dbrouter.datasource.source.DefaultDynamicDataSource;
import icu.xbg.dbrouter.interceptor.SimpleTableInterceptorBuilder;
import icu.xbg.dbrouter.meta.DefaultMetaResolver;
import icu.xbg.dbrouter.meta.MetaResolver;
import icu.xbg.dbrouter.strategy.StrategyCache;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 19:15
 */
public class SimpleDynamicDataSourceBuilder extends BaseDynamicDataSourceBuilder {

    public SimpleDynamicDataSourceBuilder(DBRouterProperties properties, DataSourceBuilderManager dataSourceBuilderManager, StrategyCache cache,MetaResolver resolver) {
        this(properties, new DefaultMetaResolver(),  (a) -> new DefaultDynamicDataSource(properties,cache,resolver),dataSourceBuilderManager);
    }

    public SimpleDynamicDataSourceBuilder(DBRouterProperties properties, MetaResolver metaResolver, DynamicDataSourceFactory factory, DataSourceBuilderManager dataSourceBuilderManager) {
        super(properties, metaResolver, factory, dataSourceBuilderManager);
    }

    @Override
    protected DataSource buildSubDataSource(DBProperties dbProperties) {
        DataSourceBuilder builder = getBuilder(dbProperties);
        return builder.buildDataSource(dbProperties);
    }

    @Override
    protected DataSource buildPrimaryDataSource(DBProperties dbProperties) {
        DataSourceBuilder builder = getBuilder(dbProperties);
        return builder.buildDataSource(dbProperties);
    }

    private DataSourceBuilder getBuilder(DBProperties prop) {
        DataSourceBuilderManager manager = getDataSourceBuilderManager();
        DataSourceMode mode = prop.getMode();
        switch (mode) {
            case SINGLE -> {
                return manager.getBuilder("SINGLE");
            }
            case POOL -> {
                // 通过名字查询，不会主动构造
                String builderName = prop.getPoolBuilderName();
                if (!StringUtils.hasLength(builderName)) {
                    builderName = "HIKARICP";
                }
                return manager.getBuilder(builderName);
            }
            default -> {
                // 自定义形式，可能需要临时构建
                Class<? extends DataSourceBuilder> poolBuilderClass = prop.getPoolBuilderClass();
                DataSourceBuilder builder = manager.getBuilder(poolBuilderClass);
                if (Objects.isNull(builder)) {
                    try {
                        Constructor<? extends DataSourceBuilder> constructor = poolBuilderClass.getConstructor();
                        constructor.setAccessible(true);
                        builder = constructor.newInstance();
                        manager.registerBuilder(builder);
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                             IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                return builder;
            }
        }
    }
}
