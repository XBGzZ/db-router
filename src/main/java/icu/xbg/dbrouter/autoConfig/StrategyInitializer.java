package icu.xbg.dbrouter.autoConfig;

import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.strategy.RouteStrategy;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.cache.DefaultStrategyCache;
import icu.xbg.dbrouter.utils.ClassLoadUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *     初始化构造缓存
 * </pre>
 *
 * @author XBG
 * @date 2024-02-04 22:04
 */
@Configuration
@EnableConfigurationProperties(DBRouterProperties.class)
public class StrategyInitializer {
    private static final String ROOT_PACKAGE = "icu.xbg.dbrouter.strategy.strategys";

    StrategyCache cache ;

    DBRouterProperties properties;


    public StrategyInitializer(@Nullable StrategyCache cache, DBRouterProperties properties) {
        this.cache = Optional.ofNullable(cache).orElse(DefaultStrategyCache.getInstance());
        this.properties = properties;
    }


    @PostConstruct
    public void loadDefaultStrategies(){
        List<Class<?>> classes = ClassLoadUtil.getClasses(ROOT_PACKAGE);
        for (Class<?> clazz:classes){
            if (RouteStrategy.class.isAssignableFrom(clazz)){
                try {
                    Constructor<?> constructor = clazz.getConstructor(StrategyCache.class,DBRouterProperties.class);
                    constructor.setAccessible(true);
                    constructor.newInstance(cache,properties);
                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                         IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
