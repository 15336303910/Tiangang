package cn.plou.web.system.baseMessage.company.dao;

import cn.plou.web.system.baseMessage.company.entity.Company;
import cn.plou.web.system.baseMessage.company.entity.CompanyKey;
import cn.plou.web.system.baseMessage.company.vo.CompanyInfo;
import cn.plou.web.system.baseMessage.company.vo.CompanyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CompanyMapper {
    int deleteByPrimaryKey(CompanyKey key);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(String companyId);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);

    List<CompanyInfo> selectAllCompany(@Param("start") Integer start,@Param("pageSize") Integer pageSize,
                                       @Param("order") String order, @Param("sortby") String sortby,
                                       @Param("companyIds") List<String> companyIds,
                                       @Param("companyVo") CompanyVo companyVo);

    int deleteBatchByIds(List<String>companyIds);

    int updateBatch(CompanyVo companyVo);

    List<Company> selectDownInfo(String companyId);

    List<Company> selectChildrenCompany(String companyId);
    List<String> selectAllCompanyIds();
    List<Company> selectAllCompanys();
    List<String> selectAllParentCompanyIds();
    Integer selectCompanyListCount(@Param("companyIds") List<String> companyIds,
                                   @Param("companyVo") CompanyVo companyVo);

    Company selectCompanyByName(String CompanyName);

		String getMaxCompanyId();
		/**************************************吕琨新增**************************************/
    List<Company> selectChildrenCompanys(@Param("companyIds") List<String> companyIds);
    List<String> selectParentIdsById(String companyId);
}