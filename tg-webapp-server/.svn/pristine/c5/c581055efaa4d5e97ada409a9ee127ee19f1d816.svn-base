package cn.plou.web.system.baseMessage.build.service;

import cn.plou.web.common.importDataBatch.ImportResult;
import cn.plou.web.system.baseMessage.build.entity.Build;
import cn.plou.web.system.baseMessage.build.vo.BuildInfo;
import cn.plou.web.system.baseMessage.build.vo.BuildSelectInfo;
import cn.plou.web.system.baseMessage.build.vo.BuildVo;
import cn.plou.web.system.baseMessage.commuity.entity.Commuity;

import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public interface BuildService {
    int addBuild(BuildInfo buildInfo);
    Build getBuildById(String buildingNo);
    int modifyBuildBatch(BuildVo buildVo);
    int modifyBuild(Build build);
    int deleteBuildBatchByIds(List<String> buildingNos);
    List<Build> getBuildTree(String commuityId);
    PageInfo<BuildInfo> getAllBuild(String companyId, String stationId, String commuityId, BuildVo buildVo, String order, String sortby, Integer page, Integer pageSize);
    Build getBuildByName(String commuityName,String buildingName,String companyId);
    ImportResult importExcel(MultipartFile multipartFile, ServletRequest request);
    String exportExcel(BuildSelectInfo buildSelectInfo,ServletRequest request);
		Integer getMaxBuildId(String commuityId);
		Map<String, Build> getBuildByIdAndToMap(Commuity commuity, Map<String, Map<String, Build>> mapBuild);
		void modifyBuildName(String address, String addressOrg, String commuityId);
}
