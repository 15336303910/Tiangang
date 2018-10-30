package cn.plou.web.mobile.station.vo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project : tg-webapp-server
 * @File : ParamGroupVo.java
 * @Author : WangJiWei
 * @Date : 2018年10月15日上午11:05:42
 *
 * @Comments :
 * 
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParamGroupVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String group;
    public ParamGroupVo(String group){
	this.group = group;
    }
//    private String groupName;
    private List<ShowDevTypeVo> showdevtypes;
    private Integer order = 0;

    /**
     * 全部页<br>
     * { 运行参数:['压力参数','温度参数','流量热量参数'] ...}
     */

}
