package icu.xbg.dbrouter.annotation;



import icu.xbg.dbrouter.autoConfig.DynamicDataSourceAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *    开关
 * </pre>
 *
 * @author XBG
 * @date 2023-12-26 16:37
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
// 自动配置开关
@Import(DynamicDataSourceAutoConfig.class)
@Deprecated
public @interface EnableRouter {
}
