package cn.plou.web.common.idgenerater.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import cn.plou.web.common.idgenerater.service.IDGenerater;
import cn.plou.web.system.baseMessage.house.vo.HouseInfo;

@RestController
@RequestMapping("extends/idGenerater")
public class IDGeneraterController {
	@Autowired
	private IDGenerater idGenerater;
	
  @PostMapping("/generateCompanyId")
	public Object generateCompanyId(){
  	return idGenerater.generateCompanyId();
  }
  
  @PostMapping("/generateCommunityId")
	public Object generateCommunityId(@RequestBody String companyId){
  	if(companyId == null){
  		return null;
  	}
  	if(companyId.contains("=")){
  		companyId = companyId.substring(companyId.indexOf("=")+1);
  	}
  	String strid = idGenerater.generateCommunityId(companyId, 1).get(0);
  	System.out.println(strid);
  	return strid;
  }
  @PostMapping("/generateProducerId")
	public Object generateProducerId(){
  	return idGenerater.generateProducerId();
  }
  
  @PostMapping("/test")
	public Object test(@RequestBody Map companyId){
  	String str = (String)companyId.get("companyId");
  	System.out.println(str);
  	return str;
  }
  
  @PostMapping("/generateStationId")
	public Object generateStationId(){
  	return idGenerater.generateStationId();
  }
  
  @PostMapping("/generateSystemId")
	public Object generateSystemId(){
  	return idGenerater.generateSystemId();
  }
  
  @PostMapping("/generateHouseIdsbyCommuityNoUpdat")
	public Object generateHouseIdsbyCommuityNoUpdat(@RequestBody  List<Map<String,String>> map){

  	Map<String, List<HouseInfo>> maps = new HashMap<>();
  	for(Map<String,String> hou:map){
  		String key = hou.get("COMMUITY_ID");
  		HouseInfo info = new HouseInfo();
  		info.setBuildingName(hou.get("BUILDING_NAME"));
  		info.setRoomName(hou.get("ROOM_NAME"));
  		info.setUnitName(hou.get("UNIT_NAME"));
  		info.setMemo1(key);
  		info.setMemo2(hou.get("CONSUMER_ID_OLD"));
  		if(maps.containsKey(key)){
  			maps.get(key).add(info);
  		}else{
  			List<HouseInfo> houses = new ArrayList<HouseInfo>();
  			houses.add(info);
  			maps.put(key, houses);
  		}
  	}
  	List<Map<String,String>> mapsRe = new ArrayList<>();
  	for(String key: maps.keySet()){
  		idGenerater.generateHouseIdsbyCommuityNoUpdat(key, maps.get(key));
  		for(HouseInfo info: maps.get(key)){
  			Map<String,String> mapRe = new HashMap<String,String>();
  			mapRe.put("UNIT_NO", info.getUnitId());
  			mapRe.put("BUILDING_NO", info.getBuildingNo());
  			mapRe.put("ROOM_NO", info.getConsumerId());
  			mapRe.put("COMMUITY_ID", info.getMemo1());		
  			mapRe.put("UNIT_NAME", info.getUnitName());
  			mapRe.put("BUILDING_NAME", info.getBuildingName());
  			mapRe.put("ROOM_NAME", info.getRoomName());
  			mapRe.put("CONSUMER_ID_OLD",  info.getMemo2());
  			mapsRe.add(mapRe);
  		}
  	}
  	return mapsRe;
	}
}
