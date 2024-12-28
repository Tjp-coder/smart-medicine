package world.tangjp.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.beans.BeanMap;

import java.util.*;

/**
 * Bean 工具
 *
 * @author Tangjp
 */
public class BeanUtil {

    // 存储BeanCopier实例的Map，用于提高性能
    private static Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    /**
     * 复制对象到另一个对象
     *
     * @param src 源对象
     * @param clazz 目标对象类
     * @param <T> 目标对象类型
     * @return 新复制的目标对象
     * @throws InstantiationException 当目标对象无法实例化时抛出
     * @throws IllegalAccessException 当目标对象无法访问时抛出
     */
    public static <T> T copy(Object src, Class<T> clazz)
            throws InstantiationException, IllegalAccessException {
        if ((null == src) || (null == clazz)) return null;
        Object des = clazz.newInstance();
        copy(src, des);
        return (T) des;
    }

    /**
     * 复制对象到另一个对象
     *
     * @param src 源对象
     * @param des 目标对象
     */
    public static void copy(Object src, Object des) {
        if ((null == src) || (null == des)) return;
        String key = generateKey(src.getClass(), des.getClass());
        BeanCopier copier = (BeanCopier) beanCopierMap.get(key);
        if (null == copier) {
            copier = BeanCopier.create(src.getClass(), des.getClass(), false);
            beanCopierMap.put(key, copier);
        }
        copier.copy(src, des, null);
    }

    /**
     * 将Map转换为指定类型的Bean
     *
     * @param map 源Map
     * @param clazz 目标Bean类
     * @param <T> 目标Bean类型
     * @return 转换后的Bean对象
     * @throws InstantiationException 当目标Bean无法实例化时抛出
     * @throws IllegalAccessException 当目标Bean无法访问时抛出
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> clazz)
            throws InstantiationException, IllegalAccessException {
        if ((null == map) || (null == clazz)) return null;
        T bean = clazz.newInstance();
        map2Bean(map, bean);
        return bean;
    }

    /**
     * 将Map转换为指定类型的Bean
     *
     * @param map 源Map
     * @param bean 目标Bean对象
     * @param <T> 目标Bean类型
     */
    public static <T> void map2Bean(Map<String, Object> map, T bean) {
        if ((null == map) || (null == bean)) return;
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
    }

    /**
     * 将Bean转换为Map
     *
     * @param bean 源Bean对象
     * @return 转换后的Map对象
     */
    public static Map<String, Object> bean2Map(Object bean) {
        if (null == bean) return null;
        return copy(BeanMap.create(bean));
    }

    /**
     * 将Bean列表转换为Map列表
     *
     * @param beanList 源Bean列表
     * @param <T> Bean类型
     * @return 转换后的Map列表
     */
    public static <T> List<Map<String, Object>> mapList(List<T> beanList) {
        if ((null == beanList) || (beanList.size() < 1)) return null;
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (T bean : beanList) {
            mapList.add(bean2Map(bean));
        }
        return mapList;
    }

    /**
     * 复制Map对象
     *
     * @param src 源Map对象
     * @param <K> Map键类型
     * @param <V> Map值类型
     * @return 复制后的Map对象
     */
    public static <K, V> Map<K, V> copy(Map<K, V> src) {
        if (null == src) return null;
        Map<K, V> des = new HashMap<>();
        des.putAll(src);
        return des;
    }

    /**
     * 将多个源对象的属性值应用到目标对象
     *
     * @param des 目标对象
     * @param srcs 源对象数组
     * @param <T> 目标对象类型
     */
    public static <T> void apply(T des, T... srcs) {
        if ((null == des) || (null == srcs) || (srcs.length < 1)) return;
        BeanMap desBeanMap = BeanMap.create(des);
        Set<?> keys = desBeanMap.keySet();
        for (T src : srcs) {
            if (null != src) {
                BeanMap srcBeanMap = BeanMap.create(src);
                for (Object key : keys) {
                    Object value = srcBeanMap.get(key);
                    if ((null != value) && (null == desBeanMap.get(key))) {
                        desBeanMap.put(des, key, value);
                    }
                }
            }
        }
    }

    /**
     * 将Map列表的属性值应用到目标对象
     *
     * @param des 目标对象
     * @param srcList 源Map列表
     * @param <T> 目标对象类型
     */
    public static <T> void apply(T des, List<Map<String, Object>> srcList) {
        if ((null == des) || (null == srcList) || (srcList.isEmpty())) return;
        BeanMap desBeanMap = BeanMap.create(des);
        Set<?> keys = desBeanMap.keySet();
        for (Map<String, Object> src : srcList) {
            if ((null != src) && (!src.isEmpty())) {
                for (Object key : keys) {
                    Object value = src.get(key);
                    if (null != value) {
                        desBeanMap.put(des, key, value);
                    }
                }
            }
        }
    }

    // 生成源类和目标类的唯一键
    private static String generateKey(Class<?> src, Class<?> des) {
        return src.getName() + des.getName();
    }

    /**
     * 将Bean对象转换为String
     *
     * @param value Bean对象
     * @param <T> Bean类型
     * @return 转换后的字符串
     */
    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 将String转换为指定类型的Bean对象
     *
     * @param str 字符串
     * @param clazz 目标Bean类
     * @param <T> 目标Bean类型
     * @return 转换后的Bean对象
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }
}
