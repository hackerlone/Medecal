package com.lhfeiyu.util.dust;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MyBatis Map类型大写下划线Key转小写驼峰形式
 *
 * @author liuzh/isea533/abel533
 * @since 1.0.0
 */
@Intercepts(
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
)
public class CameHumpInterceptor implements Interceptor {

    public static final String RESULT_TYPE = "-Inline";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //先执行，后处理
        Object result = invocation.proceed();
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        ResultMap resultMap = mappedStatement.getResultMaps().get(0);
        Class<?> type = resultMap.getType();
        //只有有返回值并且type是Map的时候,还不能是嵌套复杂的resultMap,才需要特殊处理
        if (((List) result).size() > 0
                && Map.class.isAssignableFrom(type)
                && !resultMap.hasNestedQueries()
                && !resultMap.hasNestedResultMaps()) {
            List resultList = (List) result;
            //1.resultType时
            if (resultMap.getId().endsWith(RESULT_TYPE)) {
                for (Object re : resultList) {
                    processMap((Map) re);
                }
            } else {//2.resultMap时
                for (Object re : resultList) {
                    processMap((Map) re, resultMap.getResultMappings());
                }
            }
        }
        return result;
    }

    /**
     * 处理简单对象
     *
     * @param map
     */
    private void processMap(Map map) {
        Map cameHumpMap = new HashMap();
        Iterator<Map.Entry> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            String key = (String) entry.getKey();
            String cameHumpKey = underlineToCamelhump(key.toLowerCase());
            if (!key.equals(cameHumpKey)) {
                cameHumpMap.put(cameHumpKey, entry.getValue());
                iterator.remove();
            }
        }
        map.putAll(cameHumpMap);
    }

    /**
     * 配置过的属性不做修改
     *
     * @param map
     * @param resultMappings
     */
    private void processMap(Map map, List<ResultMapping> resultMappings) {
        Set<String> propertySet = toPropertySet(resultMappings);
        Map cameHumpMap = new HashMap();
        Iterator<Map.Entry> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            String key = (String) entry.getKey();
            if (propertySet.contains(key)) {
                continue;
            }
            String cameHumpKey = underlineToCamelhump(key.toLowerCase());
            if (!key.equals(cameHumpKey)) {
                cameHumpMap.put(cameHumpKey, entry.getValue());
                iterator.remove();
            }
        }
        map.putAll(cameHumpMap);
    }

    /**
     * 列属性转Set
     *
     * @param resultMappings
     * @return
     */
    private Set<String> toPropertySet(List<ResultMapping> resultMappings) {
        Set<String> propertySet = new HashSet<String>();
        for (ResultMapping resultMapping : resultMappings) {
            propertySet.add(resultMapping.getProperty());
        }
        return propertySet;
    }

    /**
     * 将下划线风格替换为驼峰风格
     *
     * @param str
     * @return
     */
    public static String underlineToCamelhump(String str) {
        Matcher matcher = Pattern.compile("_[a-z]").matcher(str);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; matcher.find(); i++) {
            builder.replace(matcher.start() - i, matcher.end() - i, matcher.group().substring(1).toUpperCase());
        }
        if (Character.isUpperCase(builder.charAt(0))) {
            builder.replace(0, 1, String.valueOf(Character.toLowerCase(builder.charAt(0))));
        }
        return builder.toString();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
