package cn.plou.web.charge.heatingmanage.service;

import cn.plou.web.charge.heatingmanage.domain.ServeResultByType;
import cn.plou.web.charge.heatingmanage.dto.*;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeDepartmentVO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeListVO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeUserListVO;
import cn.plou.web.charge.heatingmanage.vo.HeatingServeVO;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: HeatingServeService
 * @Description: 供热服务
 * @Author: youbc
 * @Date 2018-08-15 16:05
 */
public interface HeatingServeService {

    void serviceApplication(ServiceApplicationDTO serviceApplicationDTO);

    void servicePayment(ServicePaymentDTO servicePaymentDTO);

    void serviceFeedback(ServiceFeedbackDTO serviceFeedbackDTO);

    HeatingServeVO get(String primaryId);

    PageInfo<HeatingServeListVO> list(HeatingServeSearchDTO heatingServeSearchDTO);


    List<HeatingServeUserListVO> userList(HeatingServeUserSearchDTO heatingServeUserSearchDTO);

    Integer userListCount(HeatingServeUserSearchDTO heatingServeUserSearchDTO);

    List<HeatingServeDepartmentVO> getUsers(String companyId);


    List<HeatingServeUserListVO> userListOfStation(HeatingServeUserSearchDTO heatingServeUserSearchDTO, List<String> ids);

    Integer userListCountOfStation(HeatingServeUserSearchDTO heatingServeUserSearchDTO, List<String> ids);

    List  <ServeResultByType>   getTotalResultByType(HeatingServeSearchDTO heatingServeSearchDTO);


   Integer    getCountByendState(HeatingServeSearchDTO heatingServeSearchDTO);

}
