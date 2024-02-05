# db-router

## 介绍
学习开 Spring-Boot-Starter 包开发，基于`SpringBoot 2.7.x`以及`JDK 17`，配合 Spring-Boot 实现开箱即用的数据库路由，支持快捷方便的扩展，整个项目具有两个核心注解，`@DBRouter` 和 `TBRouter`分别负责数据源的路由和库表的路由功能。

## 原理说明

该项目是基于 `AbstractRoutingDataSource`  + `JDK动态代理` 实现的路由切换和SQL拦截功能。需要使用Spring AOP 对注解处进行切面处理，获取注解中的上下文信息，将上下文信息保存在`ThreadLocal`中，实现线程间信息隔离。

## 演示

Mapper:

```java
@Mapper
public interface TBMapper extends BaseMapper<TB> {

    @Select("select count(*) tb from tb t ")
    public Integer nativeSql();
}

```

Service

```java
@Service
public class TbService {
    @Autowired
    TBMapper mapper;


    @DBRouter(keywords = "user")
    @TBRouter(keywords = "user",tableName = "tb")
    @Transactional
    public void saveUser(String user){
        TB tb = new TB();
        tb.setUser(user);
        mapper.insert(tb);
    }

    @DBRouter(keywords = "user")
    @TBRouter(keywords = "user",tableName = "tb")
    @Transactional
    public void nativeSql(String user){
        mapper.nativeSql();
    }
}
```

Test

```java
@SpringBootTest
class Demo1ApplicationTests {




    @Autowired
    TbService service;

    @Test
    void contextLoads() {
        service.nativeSql("xbg7");
    }

}
```



## 功能扩展

可支持使用者根据项目需求进行动态扩展：

- 路由策略扩展：

  - 包地址：`icu.xbg.dbrouter.strategy.strategys`

  - 功能说明：

    实现：`BaseTableRouteStrategy`、`BaseDataBaseRouteStrategy` 子类，并注入容器，会自动交给默认的策略缓存保存，在注解上指定注解后，即可从缓存中获取实例

- 缓存扩展：

  - 包地址：`icu.xbg.dbrouter.strategy.cache`

  - 功能说明：

    路由策略实现类的缓存容器，实现`StrategyCache` 接口，实现全局缓存功能的替换

- 连接池扩展：

  - 包地址：`icu.xbg.dbrouter.datasource`

  - 功能说明：

    由于不同项目的数据库连接池可能存在差异，目前默认内置`HikariCP`和`SingleConnect(单一数据连接两种模式)`，可通过注入`DataSourceBuilder`子类实例，来自定义数据源的构造方式，目前HikariCP的配置文件是非常精简的

  

### 存在缺陷

1、目前属于学习初期，项目代码缺少测试和实战检测，以及不同框架的兼容性测试，故可能会存在大量BUG

2、项目注释还需要进行优化改进

3、配置文件自动提示还需要进行优化

4、默认table拦截时，是通过直接替换文本实现，虽然简单，但是存在bug（表名和字段名一样时，会错误替换：exp:` select count(*) tb from tb t `）
