package icu.xbg.dbrouter.interceptor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  表拦截，因为表切换和数据库切换的原理不同
 *  切换表是需要拦截sql的，而且更为复杂，对性能影响最大
 *  而且适配难度最大最复杂，虽然mybatis-plus提供了表切换
 *  的拦截器，但是这个只是对mybatis-plus有效，如果想要对
 *  更多的框架兼容，肯定是需要适配spring，做sql拦截的
 * </pre>
 *
 * @author XBG
 * @date 2024-02-04 0:26
 */