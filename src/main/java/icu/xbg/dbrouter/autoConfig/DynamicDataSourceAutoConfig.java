package icu.xbg.dbrouter.autoConfig;

import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.datasource.DataSourceBuilder;
import icu.xbg.dbrouter.datasource.DataSourceBuilderManager;
import icu.xbg.dbrouter.datasource.DynamicDataSourceBuilder;
import icu.xbg.dbrouter.datasource.builder.dynamic.SimpleDynamicDataSourceBuilder;
import icu.xbg.dbrouter.datasource.builder.manager.SimpleDataSourceManager;
import icu.xbg.dbrouter.datasource.builder.strategy.HikariCPDataSourceBuilder;
import icu.xbg.dbrouter.datasource.source.BaseDynamicDataSource;
import icu.xbg.dbrouter.interceptor.SimpleTableInterceptorBuilder;
import icu.xbg.dbrouter.interceptor.TableInterceptorBuilder;
import icu.xbg.dbrouter.meta.DefaultMetaResolver;
import icu.xbg.dbrouter.meta.MetaResolver;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.cache.DefaultStrategyCache;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  动态数据源注入
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 14:05
 */
@Configuration
@EnableConfigurationProperties(DBRouterProperties.class)
public class DynamicDataSourceAutoConfig {

    @Bean
    @ConditionalOnMissingBean(MetaResolver.class)
    public MetaResolver resolverCustomCache(@Autowired(required = false) StrategyCache cache){
        if (Objects.isNull(cache)){
            cache = DefaultStrategyCache.getInstance();
        }
        return new DefaultMetaResolver(cache);
    }



    /**
     * 数据源工厂，核心的工厂，主要作用建造动态数据源
     * 将配置和子数据源构造器放入，然后注入Spring容器
     * @param properties
     * @param builderManager
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(value = {DynamicDataSourceBuilder.class, SimpleDynamicDataSourceBuilder.class})
    @ConditionalOnBean({MetaResolver.class})
    public DynamicDataSourceBuilder defaultDynamicDataSourceFactory(DBRouterProperties properties,
                                                                    DataSourceBuilderManager builderManager,
                                                                    MetaResolver resolver,
                                                                    @Autowired(required = false)TableInterceptorBuilder builder){
        if (Objects.isNull(builder)){
            builder = new SimpleTableInterceptorBuilder(resolver);
        }
        return new SimpleDynamicDataSourceBuilder(properties,builderManager,resolver,builder);
    }

    /**
     * 管理器注入，将IOC容器中的具体数据源构造器注入，如果需要实现特殊的连接池
     * 注入，那么需要向ioc中注入DataSourceBuilder然后交给管理器管理
     * @param builderProvider
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(DataSourceBuilderManager.class)
    public DataSourceBuilderManager defaultDataSourceBuilderManager(ObjectProvider<DataSourceBuilder> builderProvider){
        SimpleDataSourceManager simpleDataSourceManager = new SimpleDataSourceManager();
        Iterator<DataSourceBuilder> iterator = builderProvider.stream().iterator();
        while (iterator.hasNext()){
            simpleDataSourceManager.registerBuilder(iterator.next());
        }
        return simpleDataSourceManager;
    }








    /**
     * 注入动态数据源，从工厂中构建
     * @param factory
     * @return
     */
    @Bean
    @ConditionalOnSingleCandidate(DynamicDataSourceBuilder.class)
    @ConditionalOnMissingBean(BaseDynamicDataSource.class)
    public DataSource dynamicDataSource(DynamicDataSourceBuilder factory){
        return factory.build();
    }

}
