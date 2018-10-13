package cn.plou.web.charge.heatingmanage.service.impl;

import cn.plou.web.charge.heatingmanage.dao.MoneyClearTaskMapper;
import cn.plou.web.charge.heatingmanage.domain.MoneyClearTask;
import cn.plou.web.charge.heatingmanage.domain.TaskNumResult;
import cn.plou.web.charge.heatingmanage.service.MoneyClearTaskService;
import cn.plou.web.common.config.common.BasePageEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yinxiaochen
 * 2018/8/23 8:56
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class MoneyClearTaskServiceImpl  implements MoneyClearTaskService {
@Resource
private MoneyClearTaskMapper   moneyClearTaskMapper;


    /**
     * @param primaryIds 流水号数组
     * @return 清欠列表
     */
    @Override
    public List<MoneyClearTask> findByprimaryIds(String[] primaryIds) {
        List<MoneyClearTask>  moneyClearTasks=new ArrayList<>();
        for (String primaryId : primaryIds) {
            moneyClearTasks.add(moneyClearTaskMapper.findByprimaryId(primaryId));
        }
        return moneyClearTasks;
    }

    @Override
    public PageInfo<MoneyClearTask> getMoneyClearTaskList(String beginCreateDate,String  endCreateDate, List<String>   consumerIdList,MoneyClearTask moneyClearTask,BasePageEntity  basePageEntity) {
        PageHelper.startPage(basePageEntity.getPage(),basePageEntity.getPageSize());
        List<MoneyClearTask> list =  moneyClearTaskMapper.find(beginCreateDate,  endCreateDate,consumerIdList,moneyClearTask,basePageEntity);
        return new PageInfo<>(list);
    }


    @Override
    public List<TaskNumResult> getTaskNum(String beginCreateDate, String  endCreateDate, List<String>   consumerIdList, MoneyClearTask moneyClearTask) {
       return moneyClearTaskMapper.findNum(beginCreateDate,endCreateDate,consumerIdList,moneyClearTask) ;
    }

    @Override
    public int addMoneyClearTaskList(List<MoneyClearTask> moneyClearTasks) {
        return moneyClearTaskMapper.insertList(moneyClearTasks);
    }

    @Override
    public int updateByPrimaryKeySelective(MoneyClearTask record) {
        return moneyClearTaskMapper.updateByPrimaryKeySelective(record);
    }
}
