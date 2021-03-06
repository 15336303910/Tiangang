package cn.plou.web.charge.heatingmanage.dao;
import cn.plou.web.charge.heatingmanage.domain.TaskNumResult;
import cn.plou.web.common.config.common.BasePageEntity;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import cn.plou.web.charge.heatingmanage.domain.MoneyClearTask;

public interface MoneyClearTaskMapper {

    int deleteByPrimaryKey(String primaryId);

    int insert(MoneyClearTask record);

    int   insertList(List moneyClearTaskList);

    MoneyClearTask selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(MoneyClearTask record);

    int updateByPrimaryKey(MoneyClearTask record);


    List<MoneyClearTask> find(@Param("beginCreateDate")String beginCreateDate, @Param("endCreateDate")String endCreateDate,@Param("list")List<String> consumerIdList,@Param("task") MoneyClearTask moneyClearTask,@Param("base")  BasePageEntity basePageEntity);
    List<TaskNumResult> findNum(@Param("beginCreateDate")String beginCreateDate, @Param("endCreateDate")String endCreateDate, @Param("list")List<String> consumerIdList, @Param("task") MoneyClearTask moneyClearTask);


    MoneyClearTask  findByprimaryId(@Param("primaryId")String primaryId);


}
