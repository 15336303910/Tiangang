package cn.plou.web.heatManage.monitor.vo;

import java.math.BigDecimal;
import java.util.List;

import cn.plou.web.heatManage.monitor.domain.RunningDataTotalDO;
import cn.plou.web.system.baseMessage.house.entity.House;
import cn.plou.web.system.commonMessage.typeMst.entity.TypeMst;
import lombok.Data;

@Data
public class HouseDataVO {
	String floor;
	String houseId;
	String houseNo;
	BigDecimal houseTmp;
	BigDecimal houseTmpSet;
	BigDecimal inWaterTmp;
	BigDecimal outWaterTmp;
	BigDecimal tmpDiff;
	BigDecimal flow;
	BigDecimal topFlow;
	BigDecimal flowIndex;
	String openStatus;
	String openStatusId;
	BigDecimal adjHeatingIndex;
	Integer  index;
	
  public String getTypeNameById(List<TypeMst> types, String id) {
    for (TypeMst type : types) {
        if (type.getId().equals(id)) {
            return type.getTypeName();
        }
    }
    return "";
}
  
  public Integer getnFloor(){
  	return Integer.parseInt(floor);
  }
  
	public void setFromHouse(House house, RunningDataTotalDO data, List<TypeMst> typeNmes){
		if(house.getRoomName() != null){
				setFloor(house.getRoomName().substring(0, house.getRoomName().length() - 2));
				setIndex(Integer.parseInt(house.getRoomName().substring(house.getRoomName().length() - 2)));
		}else{
			setFloor("1");
			setIndex(1);
		}
		setHouseId(house.getConsumerId());
		setHouseNo(house.getRoomName());
		if (data != null) {
			setHouseTmp(data.getRoomTemperatureRead());
			setHouseTmpSet(data.getRoomTemperatureSet());
			setInWaterTmp(data.getInTemperature());
			setOutWaterTmp(data.getOutTemperature());
			if (data.getInTemperature() != null && data.getOutTemperature() != null) {
				setTmpDiff(data.getInTemperature().subtract(data.getOutTemperature()));
			} else {
				setTmpDiff(new BigDecimal(0));
			}
			setFlow(data.getFlowSpeed());
			setTopFlow(data.getFlowUpperLimit());
			setFlowIndex(data.getFlowingIndex());

			String typeNme = getTypeNameById(typeNmes, data.getOpenStatus());// open_status
			setOpenStatus(typeNme);
			setOpenStatusId(data.getOpenStatus());
			setAdjHeatingIndex(data.getAdjHeatingIndex());
		}
	}
}
