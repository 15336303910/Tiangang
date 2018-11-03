package cn.plou.web.heatManage.history.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.plou.common.utils.StringUtil;
import cn.plou.web.common.config.common.Cond;
import cn.plou.web.common.config.common.Root;
import cn.plou.web.common.utils.Query;
import cn.plou.web.common.utils.Tools;
import cn.plou.web.heatManage.history.service.HistoryDataService;
import cn.plou.web.system.baseMessage.house.vo.HouseSelectInfo;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 
 * @author lvkun
 */

@RestController
@RequestMapping("${heatManagePath}/history")
public class HistoryDataController {
	@Autowired
	private HistoryDataService historyDataService;
	/*
	@ResponseBody
	@GetMapping("/list")
	public PageUtils list(@RequestParam Map<String, Object> params) {
		// 查询列表数据
		Query query = new Query(params);
		Pageable pageable = null;
		List<Object> dataList = er.findAll(pageable).getContent();
		//int total = dataService.count(query);
		PageUtils pageUtils = new PageUtils(dataList, 100);
		return pageUtils;
	}
	*/
	/**
	 * 历史数据-热量表历史主要数据
	 * @param params
	 * @return
	 */
   //@ApiOperation(value = "热量表历史主要数据")
	@GetMapping("/getMeterHisData")
	public Object getMeterHisData(@RequestParam Map<String, Object> params) {
		Root root = new Root();
		try {
			
			
			setMap(params,"inWaterTmp");
			setMap(params,"outWaterTmp");
			setMap(params,"tmpDiff");
			setMap(params,"flow");
			setMap(params,"flowSpeed");
			setMap(params,"power");
			setMap(params,"heat");
			setMap(params,"readTime");
			root = historyDataService.getMeterHisData(params);	
			return root;
		} catch (Exception ex) {
			ex.printStackTrace();
			root.setCode(1);
			root.setMsg(ex.getMessage());
		}
		return root;
	}
 
 
	/**
	 * 历史数据-温控阀历史主要数据
	 * @param params
	 * @return
	 */
 @ApiOperation(value = "温控阀历史主要数据")
	@GetMapping("/getWkfHisData")
	public Object getWkfHisData(@RequestParam Map<String, Object> params){
	 Root root = new Root();
		try {
			root = historyDataService.getWkfHisData(params);	
			return root;
		} catch (Exception ex) {
			ex.printStackTrace();
			root.setCode(1);
			root.setMsg(ex.getMessage());
		}
		return root;
	}
	
	/**
	 * 历史数据-室温采集器历史主要数据
	 * @param params
	 * @return
	 */
	@GetMapping("/getHouseCjqHisData")
	public Object getHouseWkqHisData(@RequestParam Map<String, Object> params){
		Root root = new Root();
		try {
			root = historyDataService.getHouseWkqHisData(params);	
			return root;
		} catch (Exception ex) {
			ex.printStackTrace();
			root.setCode(1);
			root.setMsg(ex.getMessage());
		}
		return root;
	}
	
	/**
	 * 历史数据-用户汇总
	 * @param params
	 * @return
	 */
	@GetMapping("/getHouseTotalData")
	public Object getHouseTotalData(@RequestParam Map<String, Object> params){
		Root root = new Root();
		try {
			root = historyDataService.getHouseTotalData(params);	
			return root;
		} catch (Exception ex) {
			ex.printStackTrace();
			root.setCode(1);
			root.setMsg(ex.getMessage());
		}
		return root;
	}
	
  @RequestMapping(value="/getHouseTotalDataToExcel",method = RequestMethod.POST)
  public Root getHouseTotalDataToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
      Root root = new Root();
      root.setData(historyDataService.getHouseTotalDataToExcel(params,request));
      return root;
  }
  
  @RequestMapping(value="/getHouseWkqHisDataToExcel",method = RequestMethod.POST)
  public Root getHouseWkqHisDataToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
      Root root = new Root();
      root.setData(historyDataService.getHouseWkqHisDataToExcel(params,request));
      return root;
  }
  
  @RequestMapping(value="/getMeterHisDataToExcel",method = RequestMethod.POST)
  public Root getMeterHisDataToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
		Root root = new Root();
		setMap(params,"inWaterTmp");
		setMap(params,"outWaterTmp");
		setMap(params,"tmpDiff");
		setMap(params,"flow");
		setMap(params,"flowSpeed");
		setMap(params,"power");
		setMap(params,"heat");
		setMap(params,"readTime");
		Object obj=historyDataService.getMeterHisDataToExcel(params, request);

		if (obj.toString().indexOf("历史") > 0) {
			root.setData(obj);
		} else {
			root = (Root) obj;
		}
		return root;
  }
  
  @RequestMapping(value="/getWkfHisDataToExcel",method = RequestMethod.POST)
  public Root getWkfHisDataToExcel(@RequestBody Map<String, Object> params, ServletRequest request) {
      Root root = new Root();
      root.setData(historyDataService.getWkfHisDataToExcel(params,request));
      return root;
  }
  
	public void setMap(Map<String, Object> params, String id) {
		if (StringUtil.hasValue(Tools.getMapValue(params, id))) {
//			params.put(id, "," + Tools.getMapValue(params, id)+ ",");
		}

	}
}
