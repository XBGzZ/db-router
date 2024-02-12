package icu.xbg.dbrouter.annotation;



import icu.xbg.dbrouter.strategy.BaseDataBaseRouteStrategy;
import icu.xbg.dbrouter.strategy.strategys.DBStrategy;

import java.lang.annotation.*;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *     数据库路由策略注解
 * </pre>
 *
 * @author XBG
 * @date 2023-12-26 16:32
 */
// TODO 后续将会支持 类级别注解
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DBRouter {

    /**
     * 关键词，后续可支持Spel表达式
     * @return
     */
    String keywords() default "";

    /**
     *  表路由策略
     *  AUTO: 开启自动方式，默认，以配置文件为主，如果配置文件中
     *      没有配置默认策略，那么不路由，
     *  CUSTOM: CUSTOM策略必须填写 customStrategy指定
     *
     *  其他: 指定对应策略
     * @return
     */
    DBStrategy strategy() default DBStrategy.AUTO;

    /**
     * 提供自定义表路由策略
     * @return
     */
    Class<? extends BaseDataBaseRouteStrategy> customStrategy() default BaseDataBaseRouteStrategy.class;

}
