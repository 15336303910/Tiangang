package cn.plou.web.heatManage.monitor.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 
 * 
 * @author liuxiaodong
 * @email yigeyanse@hotmail.com
 * @date 2018-06-29 15:24:01
 */
public class WeatherDataDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//记录序号：采番
	private String rowno;
	//城市
	private String city;
	//时间
	private Date readTime;
	//温度
	private BigDecimal temperature;
	//天气
	private BigDecimal weather;
	//风速
	private BigDecimal spead;
	//光照
	private BigDecimal light;
	//湿度
	private BigDecimal humidity;
	//备注
	private String notes;
	//创建时间
	private Date createDate;
	//创建者
	private String createUser;
	//更新时间
	private Date updateDate;
	//更新者
	private String updateUser;

	/**
	 * 设置：记录序号：采番
	 */
	public void setRowno(String rowno) {
		this.rowno = rowno;
	}
	/**
	 * 获取：记录序号：采番
	 */
	public String getRowno() {
		return rowno;
	}
	/**
	 * 设置：城市
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 获取：城市
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 设置：时间
	 */
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	/**
	 * 获取：时间
	 */
	public Date getReadTime() {
		return readTime;
	}
	/**
	 * 设置：温度
	 */
	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}
	/**
	 * 获取：温度
	 */
	public BigDecimal getTemperature() {
		return temperature;
	}
	/**
	 * 设置：天气
	 */
	public void setWeather(BigDecimal weather) {
		this.weather = weather;
	}
	/**
	 * 获取：天气
	 */
	public BigDecimal getWeather() {
		return weather;
	}
	/**
	 * 设置：风速
	 */
	public void setSpead(BigDecimal spead) {
		this.spead = spead;
	}
	/**
	 * 获取：风速
	 */
	public BigDecimal getSpead() {
		return spead;
	}
	/**
	 * 设置：光照
	 */
	public void setLight(BigDecimal light) {
		this.light = light;
	}
	/**
	 * 获取：光照
	 */
	public BigDecimal getLight() {
		return light;
	}
	/**
	 * 设置：湿度
	 */
	public void setHumidity(BigDecimal humidity) {
		this.humidity = humidity;
	}
	/**
	 * 获取：湿度
	 */
	public BigDecimal getHumidity() {
		return humidity;
	}
	/**
	 * 设置：备注
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	/**
	 * 获取：备注
	 */
	public String getNotes() {
		return notes;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置：创建者
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取：创建者
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置：更新者
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	/**
	 * 获取：更新者
	 */
	public String getUpdateUser() {
		return updateUser;
	}
}
