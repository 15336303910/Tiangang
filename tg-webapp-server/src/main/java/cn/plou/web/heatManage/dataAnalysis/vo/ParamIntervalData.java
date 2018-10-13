package cn.plou.web.heatManage.dataAnalysis.vo;

import java.util.List;

/**
 * @ClassName: ParamIntervalData
 * @Description: 参数区间信息中的 data 信息
 * @Author: youbc
 * @Date 2018-08-10 14:40
 */
public class ParamIntervalData {

    private String title;

    private String columnName;

    private List<ParamIntervalDataList> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public List<ParamIntervalDataList> getList() {
        return list;
    }

    public void setList(List<ParamIntervalDataList> list) {
        this.list = list;
    }
}
