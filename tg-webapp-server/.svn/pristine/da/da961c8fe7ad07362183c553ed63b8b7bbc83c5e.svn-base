package cn.plou;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
public class TreeMapTest {
    public static void main(String[] args) {
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj2.compareTo(obj1);
                    }
                });
        map.put("2018", "ccccc");
        map.put("2017", "aaaaa");
        map.put("2019", "bbbbb");
        map.put("2020", "ddddd");
        
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();



        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + map.get(key));
        }
    }
}