package icu.xbg.dbrouter.autoConfig;

import icu.xbg.dbrouter.datasource.DataSourceBuilder;
import icu.xbg.dbrouter.datasource.builder.strategy.HikariCPDataSourceBuilder;
import icu.xbg.dbrouter.datasource.builder.strategy.SingleConnectDataSourceBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *  数据源构造器注入
 * </pre>
 *
 * @author XBG
 * @date 2024-02-05 14:58
 */
@Configuration
public class DataSourceBuilderAutoConfig {


    @Bean
    @ConditionalOnMissingBean(HikariCPDataSourceBuilder.class)
    public DataSourceBuilder hikariCPDataSourceBuilder(){
        return new HikariCPDataSourceBuilder();
    }

    @Bean
    @ConditionalOnMissingBean(SingleConnectDataSourceBuilder.class)
    public DataSourceBuilder singleConnectDataSourceBuilder(){
        return new SingleConnectDataSourceBuilder();
    }
}
