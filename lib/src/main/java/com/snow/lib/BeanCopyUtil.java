package com.snow.lib;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GongWenHua on 17.12.22.
 */
public class BeanCopyUtil {

    private static Mapper mapper = new DozerBeanMapper();

    public static <T> T createOnCopy(Object source, Class<T> clazz) {
        return mapper.map(source, clazz);
    }

    public static void copy(Object source, Object destination) {
        mapper.map(source, destination);
    }

    public static <T1 extends List, T2> List<T2> createOnListCopy(T1 source, Class<T2> elementClass) {
        List<T2> list = new ArrayList<>();
        Iterator<T2> iterator = list.iterator();
        source.forEach((e) ->
                list.add(mapper.map(e, elementClass))
        );
        return list;
    }
}
