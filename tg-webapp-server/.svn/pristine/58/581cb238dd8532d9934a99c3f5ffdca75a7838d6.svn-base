package cn.plou.web.common.utils;

import java.util.ArrayList;
import java.util.List;

public class PageInfo {

    public static List getPageInfo(List list,Integer pageNum,Integer pageSize){
        PageInfo pageInfo =new PageInfo();
        List subList = new ArrayList();
        if(list.size()-pageNum*pageSize<0) {
            subList = list.subList((pageNum-1)*pageSize,list.size());
        }else{
            subList = list.subList((pageNum-1)*pageSize,pageNum*pageSize);
        }
        return subList;
    }
}
