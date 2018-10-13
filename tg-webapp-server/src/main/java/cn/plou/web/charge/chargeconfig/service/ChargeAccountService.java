package cn.plou.web.charge.chargeconfig.service;

import cn.plou.web.charge.chargeconfig.dto.*;
import cn.plou.web.charge.chargeconfig.vo.AccountHistoryListVO;
import cn.plou.web.charge.chargeconfig.vo.ChargeAccountVO;
import cn.plou.web.charge.chargeconfig.vo.CheckAccountListVO;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ChargeAccountService
 * @Description: 收费对账
 * @Author: youbc
 * @Date 2018-08-31 15:07
 */
public interface ChargeAccountService {

    List<CheckAccountListVO> list(ChargeAccountSearchDTO chargeAccountSearchDTO);

    Integer listCount(ChargeAccountSearchDTO chargeAccountSearchDTO);

    List<AccountHistoryListVO> historyList(AccountHistoryDTO accountHistoryDTO);

    Integer historyListCount(AccountHistoryDTO accountHistoryDTO);

    ChargeAccountVO getChargeAccount(ChargeAccountDTO chargeAccountDTO);

    void save(SaveChargeAccountDTO saveChargeAccountDTO, ChargeAccountVO chargeAccountVO);

    List<Map<String, String>> bankCharge(BankChargeDTO bankChargeDTO, String fileName);

    List<CheckAccountListVO> listOfStation(ChargeAccountSearchDTO chargeAccountSearchDTO, List<String> ids);

    Integer listCountOfStation(ChargeAccountSearchDTO chargeAccountSearchDTO, List<String> ids);

    String exportList(ServletRequest request, List<CheckAccountListVO> list);
}
