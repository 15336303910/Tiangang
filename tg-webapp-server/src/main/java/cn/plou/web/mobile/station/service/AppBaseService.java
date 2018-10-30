package cn.plou.web.mobile.station.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

import cn.plou.web.common.utils.Support;
import cn.plou.web.mobile.station.vo.LineData;

/**
 * @Project : tg-webapp-server
 * @File : BaseService.java
 * @Author : WangJiWei
 * @Date : 2018年10月15日下午12:44:16
 *
 * @Comments :
 * 
 */
public abstract class AppBaseService {

    /**
     * 查询 station_data_point 指定点位时间内历史数据
     */
    public List<LineData> queryES(String sourceid, String pointid, long start, long end,
	    String orderBy, String limit) throws Exception {

	String eql = "SELECT * FROM temp_station_data_point where sourceid = '" + sourceid
		+ "' and pointid = '" + pointid + "' and sysReadTimeLong >= " + start
		+ " and sysReadTimeLong <=" + end + " order by sysReadTimeLong " + orderBy;
	if (StringUtils.isNotBlank(limit)) {
	    eql += (" " + limit);
	}
	ResultSet rs = Support.querryFromEs(eql);
	List<LineData> items = extractES(rs);
	return items;
    }

    
    private List<LineData> extractES(ResultSet rs) throws SQLException {
	List<LineData> items = null;
	if (null != rs) {
	    items = Lists.newArrayList();
	    while (rs.next()) {
		Double val = rs.getObject("val") == null ? null : rs.getDouble("val");
		Long sysReadTimeLong = rs.getObject("sysReadTimeLong") == null ? null
			: rs.getLong("sysReadTimeLong");
		if (sysReadTimeLong != null) { // 曲线断点
		    items.add(new LineData(sysReadTimeLong, val));
		}
	    }
	}
	return items;
    }

    
    public Integer queryESCount(String sourceid, String pointid, long start, long end)
	    throws SQLException {
	String eql = "SELECT count(*) as total FROM station_data_point where sourceid = '"
		+ sourceid + "' and pointid = '" + pointid + "' and sysReadTimeLong >= " + start
		+ " and sysReadTimeLong <=" + end;
	ResultSet rs = Support.querryFromEs(eql);
	if (null != rs) {
	    rs.next();
	    return rs.getObject("total") == null ? 0 : rs.getInt("total");
	}
	return 0;
    }
}