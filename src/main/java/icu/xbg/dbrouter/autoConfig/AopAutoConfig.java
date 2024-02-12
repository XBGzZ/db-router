package icu.xbg.dbrouter.autoConfig;

import icu.xbg.dbrouter.aop.DataBaseRouteJoinPoint;
import icu.xbg.dbrouter.aop.TableRouteJoinPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  切面注入
 * </pre>
 *
 * @author XBG
 * @date 2024-02-04 21:15
 */
@Configuration
public class AopAutoConfig {

    @Bean
    public DataBaseRouteJoinPoint dataBase(){
        return new DataBaseRouteJoinPoint();
    }

    @Bean
    public TableRouteJoinPoint table(){
        return new TableRouteJoinPoint();
    }
}
