package cn.plou.web.common.importDataBatch;

import cn.plou.web.system.baseMessage.commuity.entity.Commuity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ImportResult {
    String id;
    Integer totalCount;
    Integer failCount;
    List<ErrorInfo> failList;
    String errorFilePath;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public List<ErrorInfo> getFailList() {
        return failList;
    }

    public void setFailList(List<ErrorInfo> failList) {
        this.failList = failList;
    }

    public String getErrorFilePath() {
        return errorFilePath;
    }
    public void setErrorFilePath(String errorFilePath) {
        this.errorFilePath = errorFilePath;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
