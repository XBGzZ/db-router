package icu.xbg.dbrouter.autoConfig;

import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.strategy.RouteStrategy;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.cache.DefaultStrategyCache;
import icu.xbg.dbrouter.strategy.strategys.dbstrategy.DBHashRoute;
import icu.xbg.dbrouter.strategy.strategys.tbstrategy.TBHashRoute;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-04 22:04
 */
@Configuration
@EnableConfigurationProperties(DBRouterProperties.class)
public class StrategyConfig {

    @Configuration
    @ConditionalOnMissingBean(StrategyCache.class)
    static class NoCache{
        @Bean
        @ConditionalOnMissingBean(DBHashRoute.class)

        public RouteStrategy strategyHash(DBRouterProperties properties){
            return new DBHashRoute(DefaultStrategyCache.getInstance(),properties);
        }

        @Bean
        @ConditionalOnMissingBean(TBHashRoute.class)
        public RouteStrategy strategyHashDefaultCache(DBRouterProperties properties){
            return new TBHashRoute(DefaultStrategyCache.getInstance(),properties);
        }
    }

    @Configuration
    @ConditionalOnBean(StrategyCache.class)
    static class CustomCache{
        @Bean
        @ConditionalOnMissingBean(DBHashRoute.class)
        public RouteStrategy strategyHash(DBRouterProperties properties,StrategyCache cache){
            return new DBHashRoute(cache,properties);
        }

        @Bean
        @ConditionalOnMissingBean(TBHashRoute.class)
        public RouteStrategy strategyHashDefaultCache(DBRouterProperties properties,StrategyCache cache){
            return new TBHashRoute(cache,properties);
        }
    }


}
