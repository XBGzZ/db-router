package icu.xbg.dbrouter.annotation;



import icu.xbg.dbrouter.autoConfig.AopAutoConfig;
import icu.xbg.dbrouter.autoConfig.DataSourceBuilderAutoConfig;
import icu.xbg.dbrouter.autoConfig.DynamicDataSourceAutoConfig;
import icu.xbg.dbrouter.autoConfig.StrategyInitializer;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *    如果不支持factory，可以使用Enable方式注入
 * </pre>
 *
 * @author XBG
 * @date 2023-12-26 16:37
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({DynamicDataSourceAutoConfig.class, AopAutoConfig.class, StrategyInitializer.class, DataSourceBuilderAutoConfig.class})
public @interface EnableRouter {
}
