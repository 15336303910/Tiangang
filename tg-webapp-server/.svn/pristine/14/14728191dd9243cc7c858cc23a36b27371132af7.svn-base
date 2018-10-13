package cn.plou.web.heatManage.dataAnalysis.service.impl;

import cn.plou.web.common.config.common.BusinessException;
import cn.plou.web.common.utils.IDWorker;
import cn.plou.web.heatManage.dataAnalysis.dao.ParamIntervalDao;
import cn.plou.web.heatManage.dataAnalysis.service.ParamIntervalService;
import cn.plou.web.heatManage.dataAnalysis.vo.ParamInterval;
import cn.plou.web.heatManage.dataAnalysis.vo.ParamIntervalData;
import cn.plou.web.heatManage.dataAnalysis.vo.ParamIntervalDataList;
import cn.plou.web.heatManage.dataAnalysis.vo.ParamIntervalDataListColumn;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.service.TypeMstService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: ParamIntervalServiceImpl
 * @Description:
 * @Author: youbc
 * @Date 2018-08-09 15:49
 */
@Service
public class ParamIntervalServiceImpl implements ParamIntervalService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ParamIntervalDao paramIntervalDao;

    @Autowired
    private TypeMstService typeMstService;

    /**
     * @Description: 获取参数区间管理信息
     * @Param: [userId]
     * @Return: cn.plou.web.heatManage.dataAnalysis.vo.ParamInterval
     * @Author: youbc
     * @Date: 2018/8/13 11上午19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ParamInterval getParamInterval(String userId) {
        // 从数据库查找记录
        ParamInterval paramInterval = paramIntervalDao.getParamInterval(userId);
        // 若没找到，通过默认配置新建
        if (paramInterval == null) {
            paramInterval = createDefault(userId);
            int createParamInterval = paramIntervalDao.createParamInterval(paramInterval);
            logger.info("createParamInterval:" + createParamInterval);
            if (createParamInterval != 1) {
                logger.error("参数区间管理信息创建失败，createParamInterval = " + createParamInterval);
                throw new BusinessException("参数区间管理信息创建失败！");
            }
        }
        return paramInterval;
    }

    /**
     * @Description: 更新参数区间管理信息
     * @Param: [id, data, userId]
     * @Return: void
     * @Author: youbc
     * @Date: 2018/8/13 15下午14
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateParamInterval(String id, String data, String userId) {
        // 从数据库查找记录
        ParamInterval paramInterval = paramIntervalDao.getParamInterval(userId);
        if (paramInterval == null) {
            logger.error("参数区间管理信息获取失败！");
            throw new BusinessException("参数区间管理信息获取失败！");
        } else {
            String createUser = paramInterval.getCreateUser();
            if (StringUtils.equals(createUser, userId)) {
                paramInterval.setData(data);
                paramInterval.setUpdateDate(new Date());
                paramInterval.setUpdateUser(userId);
                int updateParamInterval = paramIntervalDao.updateParamInterval(paramInterval);
                logger.info("updateParamInterval:" + updateParamInterval);
                if (updateParamInterval != 1) {
                    logger.error("参数区间管理信息更新失败，updateParamInterval = " + updateParamInterval);
                    throw new BusinessException("参数区间管理信息更新失败！");
                }
            } else {
                logger.error("不能修改他人的参数区间管理信息！");
                throw new BusinessException("不能修改他人的参数区间管理信息！");
            }
        }
    }

    /**
     * @Description: 若当前用户无信息，则根据默认配置创建
     * @Param: [userId]
     * @Return: cn.plou.web.heatManage.dataAnalysis.vo.ParamInterval
     * @Author: youbc
     * @Date: 2018/8/13 11上午20
     */
    private ParamInterval createDefault(String userId) {
        // 从 type_mst 表取 type_kbn = param_interval，拿到 type_name 列表
        List<ParamIntervalData> data = new ArrayList<>(); // param_interval 表 data 字段的数据
        List<String> listKbn = new ArrayList<String>();
        listKbn.add("param_interval");
        List<TypeMst> info = typeMstService.getDownInfoByTypeKbns(listKbn);
        for (TypeMst typeMst : info) {
            data.add(buildData(typeMst.getTypeName()));
        }

        ParamInterval paramInterval = new ParamInterval();
        paramInterval.setId(IDWorker.uuid());
        paramInterval.setData(JSON.toJSONString(data, SerializerFeature.WriteMapNullValue));
        paramInterval.setCreateUser(userId);
        Date date = new Date();
        paramInterval.setCreateDate(date);
        paramInterval.setUpdateUser(userId);
        paramInterval.setUpdateDate(date);
        return paramInterval;
    }

    /**
     * @Description: 构建 data 数据
     * @Param: [title]
     * @Return: cn.plou.web.heatManage.dataAnalysis.vo.ParamIntervalData
     * @Author: youbc
     * @Date: 2018/8/13 11上午20
     */
    private ParamIntervalData buildData(String title) {
        ParamIntervalData paramIntervalData = new ParamIntervalData();
        paramIntervalData.setTitle(title + "参数区间管理");
        paramIntervalData.setColumnName(title + "区间");
        List<ParamIntervalDataList> list = new ArrayList<>();
        String[] types = {"A", "B", "C"};
        for (String type : types) {
            ParamIntervalDataList paramIntervalDataList = new ParamIntervalDataList();
            paramIntervalDataList.setName(title + "参数区间" + type);
            List<ParamIntervalDataListColumn> columns = new ArrayList<>();
            ParamIntervalDataListColumn paramIntervalDataListColumn1 = new ParamIntervalDataListColumn();
            paramIntervalDataListColumn1.setMin(null);
            paramIntervalDataListColumn1.setMax(10);
            columns.add(paramIntervalDataListColumn1);
            ParamIntervalDataListColumn paramIntervalDataListColumn2 = new ParamIntervalDataListColumn();
            paramIntervalDataListColumn2.setMin(10);
            paramIntervalDataListColumn2.setMax(null);
            columns.add(paramIntervalDataListColumn2);
            paramIntervalDataList.setColumn(columns);
            list.add(paramIntervalDataList);
        }
        paramIntervalData.setList(list);
        return paramIntervalData;
    }
}
