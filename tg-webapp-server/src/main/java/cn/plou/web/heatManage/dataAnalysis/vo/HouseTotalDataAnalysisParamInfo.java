package cn.plou.web.heatManage.dataAnalysis.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HouseTotalDataAnalysisParamInfo {

    private String type;
    private String id;
    private String startTime;
    private String endTime;
    private String standard;
    private List<HouseTotalDataAnalysisParamRangeInfo> ranges;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public List<HouseTotalDataAnalysisParamRangeInfo> getRanges() {
        return ranges;
    }

    public void setRanges(List<HouseTotalDataAnalysisParamRangeInfo> ranges) {
        this.ranges = ranges;
    }


    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }


}
