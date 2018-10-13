package cn.plou;

import cn.plou.web.charge.chargeconfig.controller.DataInitController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author yinxiaochen
 * 2018/9/4 8:49
 */
public class JrebelTest {

    public static void main(String[] args) {
        //http://jrebel.autoseasy.cn/xixihaha/efc4d6dd-67e8-4895-9182-d93c563f7de1
        /*System.out.println(UUID.randomUUID());*/
        long  t1 = System.currentTimeMillis();
        long  sum =0;
        for (int i = 0; i < 4000; i++) {
            for (int i1 = 0; i1 < 4000; i1++) {
                sum= sum+i*i1;
            }

        }
        long  t2 = System.currentTimeMillis();
        System.out.println(sum);
        System.out.println((double)(t2-t1)/1000);
    }


}
