package cn.plou.web.charge.heatingmanage.service;

import cn.plou.web.charge.heatingmanage.domain.HouseHeatDetail;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;


/**
 * 2018/8/21 15:12
 */
public interface HouseHeatDetailService {


    PageInfo<HouseHeatDetail> getHouseHeatDetailList( String id, String keyWord,  String annual,String sortby,String order,Integer page,Integer pageSize  );
    PageInfo<HouseHeatDetail> findByUser(String consumerId,String annual, String order, String sortby, Integer page, Integer pageSize);

    BigDecimal getSumMeterRead(String consumerId,String annual);
    BigDecimal getSumHeatAdj(String consumerId,String annual);
}
