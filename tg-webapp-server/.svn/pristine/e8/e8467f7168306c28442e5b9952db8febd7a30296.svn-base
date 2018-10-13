package cn.plou.web.charge.heatingmanage.service.impl;

import cn.plou.web.charge.heatingmanage.dao.HeatCheckDetailMapper;
import cn.plou.web.charge.heatingmanage.domain.HeatCheckDetail;
import cn.plou.web.charge.heatingmanage.service.HeatCheckDetailService;
import cn.plou.web.common.config.common.BasePageEntity;
import cn.plou.web.common.utils.FlowIdTool;
import cn.plou.web.common.utils.a1.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author yinxiaochen
 * 2018/8/23 8:56
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class HeatCheckDetailServiceImpl  implements HeatCheckDetailService {


    @Resource
    private HeatCheckDetailMapper  heatCheckDetailMapper;


    @Override
    public PageInfo<HeatCheckDetail> getList(String beginCreateDate,String  endCreateDate,HeatCheckDetail heatCheckDetail, BasePageEntity basePageEntity) {
        PageHelper.startPage(basePageEntity.getPage(),basePageEntity.getPageSize());
        List<HeatCheckDetail> list =  heatCheckDetailMapper.find(beginCreateDate,endCreateDate,heatCheckDetail,basePageEntity);
        return new PageInfo<>(list);
    }

    @Override
    public int addHeatCheckDetail(HeatCheckDetail heatCheckDetail) {

        heatCheckDetail.setPrimaryId(heatCheckDetail.getConsumerId()+""+DateUtil.toDateTimeString(new Date(),"yyyyMMdd"));
        return heatCheckDetailMapper.insert(heatCheckDetail);
    }
}
