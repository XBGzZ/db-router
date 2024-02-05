package icu.xbg.dbrouter.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <pre style="color:#51c4d3">
 *
 * </pre>
 *
 * @author XBG
 * @date 2024-02-04 16:53
 */
public class ArgsUtil {
    public static Map<String, Object> resolveParam(ProceedingJoinPoint joinPoint) {
        HashMap<String, Object> argsMap = new HashMap<>();
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        if (signature instanceof MethodSignature methodSignature){
            String[] parameterNames = methodSignature.getParameterNames();
            for (int i=0;i<args.length;i++){
                argsMap.put(parameterNames[i],args[i]);
            }
        }else {
            for (int i=0;i<args.length;i++){
                argsMap.put("arg"+i,args[i]);
            }
        }
        return argsMap;
    }
}
