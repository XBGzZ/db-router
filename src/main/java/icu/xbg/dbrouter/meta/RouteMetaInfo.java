package icu.xbg.dbrouter.meta;

import icu.xbg.dbrouter.strategy.BaseDataBaseRouteStrategy;
import icu.xbg.dbrouter.strategy.BaseTableRouteStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-02 20:13
 */
@Setter
@Getter
public class RouteMetaInfo implements RouteMeta{
     String dataSourceName;
     String[] tableName;
     Integer tableCount;
     String tbKeywords;
     String dbKeywords;
     Map<String,Object> param;
     Class<? extends BaseTableRouteStrategy> tableRouteStrategy;
     Class<? extends BaseDataBaseRouteStrategy> dataBaseRouteStrategy;

     public void setParam(Map<String, Object> param) {
          if (Objects.isNull(this.param)){
               this.param = param;
          }else {
               this.param.putAll(param);
          }
     }
}
