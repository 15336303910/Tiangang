package cn.plou.web.system.commonMessage.typeMst.service;

import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMstKey;
import cn.plou.web.system.commonMessage.typeMst.vo.TypeMstVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TypeMstService {
    TypeMst getTypeMstById(String id);

    int addTypeMst(TypeMst typeMst);

    List<TypeMst> getDownInfoByTypeKbns(List<String> typeKbn);

    PageInfo<TypeMst> getTypeMstByTypeKbn(String typeKbn, TypeMstVo typeMstVo, String order, String sortby, Integer page, Integer pageSize);

    int modifyTypeMstBatch(TypeMstVo typeMstVo);

    int modifyTypeMst(TypeMst typeMst);

    int deleteTypeMstBatchByIds(List<TypeMstKey> typeMstKeys);

    List<TypeMst> getTypeMstAllType();

    String typeNameByTypeId(List<TypeMst> types, String typeed, String typeId);

    String getTypeNameById(List<TypeMst> types, String id);

    List<TypeMst> getAllTypeMst();

    List<TypeMst> getAllTypeMstByRedis();
}
