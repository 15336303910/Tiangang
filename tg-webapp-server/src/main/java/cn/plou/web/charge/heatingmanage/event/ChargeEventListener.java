package cn.plou.web.charge.heatingmanage.event;

import cn.plou.web.charge.chargeconfig.controller.DataInitController;
import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
import cn.plou.web.charge.chargeconfig.service.UserYearHeatService;
import cn.plou.web.charge.chargeconfig.service.UserYearReceivableDetailService;
import cn.plou.web.common.eventhandle.TGEvent;
import cn.plou.web.common.eventhandle.TGEventListener;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.baseMessage.house.service.HouseService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 收费业务监听器
 * @author yinxiaochen
 * 2018/8/22 9:31
 */
@Component

public class ChargeEventListener implements TGEventListener<TGEvent> {

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */



    @Resource
    private  HouseService  houseService;
    @Resource
    private UserYearHeatService userYearHeatService;
    @Resource
    private  UserYearReceivableDetailService   userYearReceivableDetailService;
    @Resource
    DataInitController  dataInitController  ;



    @Override
    public void onApplicationEvent(TGEvent event) {
        if (event  instanceof NeedMoneyEvent){//测试事件

            System.out.println("监听成功，进行处理");


        }else if(event  instanceof   PriceDefineChangeEvent){

            /**
             * 王磊说需要更新以下四个表
             *
             * 1用户基本信息（存疑，为什么要更新这个表？）
              2用户年度采暖费用明细表
              3用户年度采暖信息表
              4供热稽查明细表
             */

            PriceDefine   priceDefine= (PriceDefine)event.getSource();
            List<House>   houseList=houseService.findByChargeType(priceDefine.getPrimaryId());

            if(houseList!=null&&houseList.size()!=0){
               StringBuilder consumerIds= new StringBuilder();

                for (House house : houseList) {
                    consumerIds.append(house.getConsumerId()).append(",");
                }
                consumerIds = new StringBuilder(consumerIds.substring(0, consumerIds.length() - 1));
               // 2用户年度采暖费用明细表
                //删除掉所有Receivable
                userYearReceivableDetailService.deleteByConsumerIds(consumerIds.toString(),null,priceDefine.getAnnual());//这个方法传的竟然是字符串，费两道事

                //重新生成Receivable

                dataInitController.initUserYearReceivableDetailData(priceDefine.getAnnual(), consumerIds.toString(),true);

                //3用户年度采暖信息表
                //删除掉所有 userYearHeat
                userYearHeatService.deleteByConsumerIds(consumerIds.toString(),null,priceDefine.getAnnual());//这个方法传的竟然是字符串，费两道事
                //重新生成 userYearHeat
                dataInitController.initUserYearHeatData(priceDefine.getAnnual(), consumerIds.toString(),true);

            }

        }

    }
}
