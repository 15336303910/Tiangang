package cn.plou.web.charge.heatingmanage.domain;

import lombok.Data;

/**
 * @author yinxiaochen
 * 2018/10/11 15:03
 */
@Data
public class ServeResultByType {
    private  String taskType;
    private  String taskTypeName;
    private Integer applyNum;
    private Integer    endNum;

}
