package cn.plou.web.charge.chargeconfig.dao;

import cn.plou.web.charge.chargeconfig.dto.AccountHistoryDTO;
import cn.plou.web.charge.chargeconfig.dto.ChargeAccountDTO;
import cn.plou.web.charge.chargeconfig.entity.ReconciliationsHistory;
import cn.plou.web.charge.chargeconfig.vo.AccountHistoryListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description: 对账历史
 * @Param:
 * @Return:
 * @Author: youbc
 * @Date: 2018/8/31 14下午23
 */
@Mapper
public interface ReconciliationsHistoryMapper {

    int deleteByPrimaryKey(String primaryId);

    int insert(ReconciliationsHistory record);

    int insertSelective(ReconciliationsHistory record);

    ReconciliationsHistory selectByPrimaryKey(String primaryId);

    int updateByPrimaryKeySelective(ReconciliationsHistory record);

    int updateByPrimaryKey(ReconciliationsHistory record);

    List<AccountHistoryListVO> getListBySearch(AccountHistoryDTO accountHistoryDTO);

    Integer getListCountBySearch(AccountHistoryDTO accountHistoryDTO);

    ReconciliationsHistory getDetail(ChargeAccountDTO chargeAccountDTO);
}