package cn.plou.web.system.commonMessage.pageGridUser.service;

import cn.plou.web.system.commonMessage.pageGrid.entity.PageGridKey;
import cn.plou.web.system.commonMessage.pageGridUser.entity.PageGridUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PageGridUserService {
    List<PageGridUser> getAllPageGridUserByUserId(String userId);
    PageGridUser getPageGridUserByKey(PageGridKey pageGridKey,String userId);
    int addPageGridUser(PageGridUser pageGridUser);
    String getNewId();
    int deleteByPrimaryKey(PageGridKey pageGridKey);
    int modifyByPrimaryKey(PageGridUser pageGridUser);
}
