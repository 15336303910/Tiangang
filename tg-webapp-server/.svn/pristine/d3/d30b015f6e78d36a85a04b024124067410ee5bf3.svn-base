package cn.plou.web.system.commonMessage.pageGridUser.service.impl;

import cn.plou.web.common.utils.UserUtils;
import cn.plou.web.system.commonMessage.pageGrid.entity.PageGridKey;
import cn.plou.web.system.commonMessage.pageGridUser.dao.PageGridUserMapper;
import cn.plou.web.system.commonMessage.pageGridUser.entity.PageGridUser;
import cn.plou.web.system.commonMessage.pageGridUser.service.PageGridUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cn.plou.web.common.utils.Tools.getMaxIdNoSize;

@Component
public class PageGridUserServiceImpl implements PageGridUserService {
    @Autowired
    private PageGridUserMapper pageGridUserMapper;

    @Override
    public List<PageGridUser> getAllPageGridUserByUserId(String userId) {
        return pageGridUserMapper.selectAllPageGridUser(userId);
    }

    @Override
    public PageGridUser getPageGridUserByKey(PageGridKey pageGridKey,String userId) {
        return pageGridUserMapper.selectByPrimaryKey(pageGridKey,userId);
    }

    @Override
    public int addPageGridUser(PageGridUser pageGridUser) {
        pageGridUser.setId(getNewId());
        return pageGridUserMapper.insertSelective(pageGridUser);
    }

    @Override
    public String getNewId() {
        if(pageGridUserMapper.selectAllIds().size()==0){
            return "1";
        }else{
            return getMaxIdNoSize(pageGridUserMapper.selectAllIds());
        }
    }

    @Override
    public int deleteByPrimaryKey(PageGridKey pageGridKey) {
        return pageGridUserMapper.deleteByPrimaryKey(pageGridKey);
    }

    @Override
    public int modifyByPrimaryKey(PageGridUser pageGridUser) {
        return pageGridUserMapper.updateByPrimaryKeySelective(pageGridUser);
    }
}
