package cn.plou.web.charge.chargeconfig.service.impl;

import cn.plou.web.charge.chargeconfig.dao.SmsInfoMapper;
import cn.plou.web.charge.chargeconfig.entity.SmsInfo;
import cn.plou.web.charge.chargeconfig.service.SmsInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author yinxiaochen
 * 2018/8/15 15:38
 */


@Service
@Transactional(rollbackFor = Exception.class)
public class SmsInfoServiceImpl  implements SmsInfoService {


    @Resource
    private SmsInfoMapper  smsInfoMapper;
    @Override
    public int deleteByPrimaryKey(String primaryId) {
        return 0;
    }

    @Override
    public int insert(SmsInfo record) {
        return 0;
    }

    @Override
    public int insertSelective(SmsInfo record) {
        return 0;
    }

    @Override
    public SmsInfo selectByPrimaryKey(String primaryId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(SmsInfo record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(SmsInfo record) {
        return 0;
    }

    @Override
    public PageInfo<SmsInfo> getSmsInfoList(Map<String, Object> map) {

        int page =  Integer.parseInt((String) map.get("page")) ;
        int pageSize = Integer.parseInt((String) map.get("pageSize"));
        PageHelper.startPage(page,pageSize);
        List<SmsInfo> infoList = smsInfoMapper.findBycreateDatebetweenOrEqualTo(map);
        return    new PageInfo<>(infoList);
    }
}
