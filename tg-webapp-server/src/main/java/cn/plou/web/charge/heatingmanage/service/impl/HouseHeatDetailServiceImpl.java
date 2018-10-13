package cn.plou.web.charge.heatingmanage.service.impl;

import cn.plou.web.charge.heatingmanage.dao.HouseHeatDetailMapper;
import cn.plou.web.charge.heatingmanage.domain.HouseHeatDetail;
import cn.plou.web.charge.heatingmanage.service.HouseHeatDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 2018/8/21 15:11
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class HouseHeatDetailServiceImpl  implements HouseHeatDetailService {

    @Resource
    private HouseHeatDetailMapper   houseHeatDetailMapper;
    @Override
    public PageInfo<HouseHeatDetail> getHouseHeatDetailList(String id,String keyWord, String annual,String sortby,String order, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<HouseHeatDetail> list = houseHeatDetailMapper.find(id,keyWord,annual,sortby,order);
        return    new PageInfo<>(list);
    }


    @Override
    public PageInfo<HouseHeatDetail> findByUser(String consumerId,String annual, String order, String sortby, Integer page, Integer pageSize){

        PageHelper.startPage(page,pageSize);
        List<HouseHeatDetail> list = houseHeatDetailMapper.findByUser(consumerId,annual,sortby,order);
        return    new PageInfo<>(list);
    }


    @Override
    public BigDecimal getSumMeterRead(String consumerId,String annual){
        return houseHeatDetailMapper.getSumMeterRead(consumerId,annual);
    }

    @Override
    public BigDecimal getSumHeatAdj(String consumerId,String annual){
        return houseHeatDetailMapper.getSumHeatAdj(consumerId,annual);
    }


}
