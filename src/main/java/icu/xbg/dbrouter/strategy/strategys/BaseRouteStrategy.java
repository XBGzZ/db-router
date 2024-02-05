package icu.xbg.dbrouter.strategy.strategys;

import icu.xbg.dbrouter.config.DBProperties;
import icu.xbg.dbrouter.config.DBRouterProperties;
import icu.xbg.dbrouter.strategy.RouteStrategy;
import icu.xbg.dbrouter.strategy.StrategyCache;
import icu.xbg.dbrouter.strategy.cache.DefaultStrategyCache;
import icu.xbg.dbrouter.utils.BeanValidateUtils;
import icu.xbg.dbrouter.utils.HashCheckUtil;
import icu.xbg.dbrouter.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;


import java.util.*;
import java.util.stream.Stream;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 18:32
 */
@Slf4j
public abstract class BaseRouteStrategy implements RouteStrategy {
    protected StrategyCache cache;

    protected DBRouterProperties properties;

    protected DBProperties primaryDB;

    protected Map<String,DBProperties> poolDB;
    protected Boolean hashCheck;
    protected Integer poolSize;

    protected List<DBProperties> poolDBProperties;
    public BaseRouteStrategy(DBRouterProperties properties){
        this(DefaultStrategyCache.getInstance(),properties);
    }
    public BaseRouteStrategy(StrategyCache cache,DBRouterProperties properties){
        this.cache = cache;
        this.properties = properties;
        hashCheck = Optional.ofNullable(properties.getHashCheck()).orElse(false);
        poolSize = properties.getDbPool().size();
        poolDBProperties = properties.getDbPool();
        poolDB = new HashMap<>();
        init(properties);
        register();
    }

    private void init(DBRouterProperties properties) {
        // 默认名字
        String dbName = "primary";
        DBProperties primaryDbConfig = properties.getPrimaryDb();
        List<DBProperties> poolDbConfig = properties.getDbPool();

        // 加载数据池
        poolDbConfig.forEach(item->poolDB.put(item.getDbName(),item));

        // 主库加载
        if (Objects.nonNull(primaryDbConfig)){
            // 主库配置不为空情况
            String configDbName = primaryDbConfig.getDbName();
            if (StringUtils.hasLength(configDbName)){
                dbName = configDbName;
            }
            // 只要配了名字就会主动去数据库池查找
            DBProperties alternateConfiguration = poolDB.getOrDefault(dbName, primaryDbConfig);
            conflictDetection(primaryDbConfig,alternateConfiguration);
            // 不存在冲突
            primaryDbConfig = alternateConfiguration;
        }else {
            primaryDbConfig = poolDbConfig.get(0);
        }
        primaryDB = primaryDbConfig;
    }

    private void conflictDetection(DBProperties primaryDbConfig,DBProperties poolDbConfig){
        if (primaryDbConfig==poolDbConfig) {
            return;
        }
        if (!StringUtils.hasLength(primaryDbConfig.getDbName())
        && !StringUtils.hasLength(primaryDbConfig.getDbName())){
            String aNull = String.format("A conflict has been detected and there are two DBProperties with the same name and different configurations [%s]", "null");
            log.error(aNull);
            throw new RuntimeException(aNull);
        }
        String primDBName = Optional.of(primaryDbConfig.getDbName()).orElse("");
        String poolDBName = Optional.of(poolDbConfig.getDbName()).orElse("");
        if (primDBName.equals(poolDBName)){
            boolean conflict = false;
            try {
                conflict = !BeanValidateUtils.onlySpecifiedFileHasValue(primaryDbConfig,new String[]{"dbName","tbCount","mode"});
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (conflict){
                throw new RuntimeException("There is a conflict between the primary database and the database pool");
            }
        }else {
            return;
        }

    }




    private void register(){
        if (Objects.isNull(cache)){
            throw new RuntimeException("Cache Can't be Null");
        }
        cache.registerStrategy(this);
    }

    // 针对多参数情况
    @Override
    public String getKey(String[] keyword, Map<String, Object> args) {
        // 参数hash重写校验
        if (hashCheck){
            hashCheck(keyword,args);
        }
        return calculateKey(Stream.of(keyword),args);
    }

    // 针对单参数情况
    @Override
    public String getKey(String keyword, Map<String, Object> args) {
        if (hashCheck){
            hashCheck(keyword,args);
        }
        return calculateKey(Stream.of(keyword),args);
    }

    public void hashCheck(String[] keywords, Map<String, Object> args){
        for (String keyword:keywords){
            hashCheck(keyword,args);
        }
    }
    public void hashCheck(String keyword, Map<String, Object> args){
        Object value = args.get(keyword);
        if (Objects.isNull(value)){
            return;
        }
        HashCheckUtil.hasHashCodeOverride(value.getClass());
    }
    protected abstract String calculateKey(Stream<String> provider, Map<String, Object> args);
}
