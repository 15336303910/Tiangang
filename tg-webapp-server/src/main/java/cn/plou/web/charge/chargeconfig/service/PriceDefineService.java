package cn.plou.web.charge.chargeconfig.service;

import java.math.BigDecimal;
import java.util.List;
import cn.plou.web.charge.chargeconfig.entity.PriceDefine;
public interface PriceDefineService{

    int addNewPriceDefine(PriceDefine priceDefine);



    int update(PriceDefine priceDefine);

    int delete(String primaryId);


    List<PriceDefine> findBycompanyId(String companyId);



    //获取某一条热价当前的状态 ，用来更新用
    PriceDefine selectByPrimaryKey(String primaryId);

    //PriceDefineUseInHouse selectByAnnualAndConsumerId(String annual,String consumerId);
    //PriceDefineUseInHouse selectByAnnualAndCompanyId(String annual,String companyId);
    List<PriceDefine>  selectByAnnualAndCompanyIds(String annual,String companyIds);
    List<PriceDefine>  selectByAnnualAndCommunityIds(String annual,String commuityIds);
    List<PriceDefine>  selectByAnnualAndStationIds(String annual,String stationIds);
    List<PriceDefine>  selectByAnnualAndConsumerIds(String annual,String consumerIds);

    /**
     .* @param primaryId
     * @param approveRes
     *
     * 审核单条热价
     */
    void    approvalSinglePriceDefine(String  primaryId,String  approveRes);



    String findCurrentHeatAnnual(String  companyId);


    int updateByPrimaryKey(PriceDefine record);




    int updateTmpValueByPrimaryKey(BigDecimal prePriceTmp,BigDecimal areaPriceTmp,BigDecimal heatPriceTmp,BigDecimal exchangerPriceTmp,String  primaryId);

}
