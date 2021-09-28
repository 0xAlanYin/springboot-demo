package com.example.alan.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.*;

import java.util.List;

/**
 * @author Alan Yin
 * @date 2021/7/29
 */

public class GuavaDemo {

    public static void main(String[] args) {

        List<String> listStr = testString();

        List<Integer> listInteger = testInteger();

        testMap(listStr);

        testBiMap(listInteger);
    }

    private static void testBiMap(List<Integer> list) {
        BiMap<String, Integer> words = HashBiMap.create();
        words.put("First", 1);
        words.put("Second", 2);
        words.put("Third", 3);

        System.out.println(words.get("Second").intValue());
        System.out.println(words.inverse().get(3));

        ImmutableMap<Integer, Object> map = Maps.toMap(list.listIterator(), a -> a + "-value");
        print(map);
    }

    private static void testMap(List<String> list) {
//        Map map = list.stream().collect(Collectors.toMap(a -> a, a -> a + 1));
        Multimap<String, String> multimap = ArrayListMultimap.create();
        list.forEach(a -> multimap.put(a, a + 1));
        print(multimap);
    }

    /**
     * 更强大的集合操作：简化相应的操作
     */
    private static List<Integer> testInteger() {
        List<Integer> list = Lists.newArrayList(2, 3, 6, 4, 1, 9, 8);
        List<List<Integer>> lists = Lists.partition(list, 3);
        print(lists);
        return list;
    }

    private static void print(Object object) {
        System.out.println(JSON.toJSONString(object));
    }

    private static List<String> testString() {
        // 字符串处理
        List<String> list = Lists.newArrayList("a", "b", "g", "1", "2");
        String result = Joiner.on(",").join(list);
        System.out.println(result);

        String test = "4489,erwr,2434,reg,5345";
        list = Splitter.on(",").splitToList(test);
        System.out.println(list);
        return list;
    }


}
