package icu.xbg.dbrouter.datasource.builder.manager;

import icu.xbg.dbrouter.datasource.DataSourceBuilder;
import icu.xbg.dbrouter.datasource.DataSourceBuilderManager;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-03 18:00
 */
@Slf4j
public abstract class BaseDataSourceManager implements DataSourceBuilderManager {

    // 单一数据源构建工厂
    protected final HashMap<String, DataSourceBuilder> nameTypeMap = new HashMap<>();

    protected final HashMap<Class<? extends DataSourceBuilder>,DataSourceBuilder> typeInstanceMap = new HashMap<>();

    @Override
    public DataSourceBuilder getBuilder(String name) {
        return nameTypeMap.get(name);
    }

    @Override
    public DataSourceBuilder getBuilder(Class<? extends DataSourceBuilder> type) {
        return typeInstanceMap.get(type);
    }

    @Override
    public void registerBuilder(DataSourceBuilder dataSourceBuilder) {
        register(dataSourceBuilder);
    }

    private void register(DataSourceBuilder dataSourceBuilder){
        if (nameTypeMap.containsKey(dataSourceBuilder.getBuilderName())||typeInstanceMap.containsKey(dataSourceBuilder.getClass())){
            log.error("The dataDataSourceBuilder [ name:{} , class:{}] is registered repeatedly",dataSourceBuilder.getBuilderName(),dataSourceBuilder.getClass());
            throw new RuntimeException("The dataDataSourceBuilder is registered repeatedly");
        }
        nameTypeMap.put(dataSourceBuilder.getBuilderName(), dataSourceBuilder);
        typeInstanceMap.put(dataSourceBuilder.getClass(),dataSourceBuilder);
    }

    @Override
    public DataSourceBuilder remove(String name) {
        DataSourceBuilder target = nameTypeMap.remove(name);
        if (Objects.nonNull(target)){
            return remove(target.getClass());
        }
        return null;
    }

    @Override
    public DataSourceBuilder remove(Class<? extends DataSourceBuilder> type) {
        DataSourceBuilder target = typeInstanceMap.remove(type);
        if (Objects.nonNull(target)){
            return remove(target.getBuilderName());
        }
        return null;
    }
}
