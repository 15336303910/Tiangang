package cn.plou.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LikeHashMap<K,V> extends HashMap<K,V> {

	 

    public List<V> getValues(Object key) {

        List<V> values = new ArrayList<V>();

        K [] keys = null;

        Set<K> set = this.keySet();

        keys  = (K [])set.toArray();

        Arrays.sort(keys);

        for(int i = 0 ;i < keys.length;i++){

            if(keys[i].toString().indexOf(key.toString()) == -1 && values.size() <1){

                continue;

            }else if(keys[i].toString().indexOf(key.toString()) != -1 ){

                values.add(this.get(keys[i]));

            }else{

                break;

            }

        }

        return values;

    }
}
