package cn.plou.web.charge.smscenter.entity;

import java.util.Random;

/**
 * Created by hxfree on 2016/4/27.
 */
public class RandomCode {

    /**
     * 生成随机码
     * @return
     */
    public static String randomCodeNum(int count){
        StringBuilder code = new StringBuilder();
        for(int i=0;i<count;i++){
            code.append(new Random().nextInt(9));
        }
        return code.toString();
    }
}
