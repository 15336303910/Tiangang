package cn.plou.web.charge.chargeconfig.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table price_define
 *
 * @mbg.generated do_not_delete_during_merge
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PriceDefine {
    /**
     * 热费单价ＩＤ
     */
    private String primaryId;

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getPrePrice() {
        return prePrice;
    }

    public void setPrePrice(String prePrice) {
        this.prePrice = prePrice;
    }

    public String getAreaPrice() {
        return areaPrice;
    }

    public void setAreaPrice(String areaPrice) {
        this.areaPrice = areaPrice;
    }

    public String getHeatPrice() {
        return heatPrice;
    }

    public void setHeatPrice(String heatPrice) {
        this.heatPrice = heatPrice;
    }

    public Integer getYeatFlag() {
        return yeatFlag;
    }

    public void setYeatFlag(Integer yeatFlag) {
        this.yeatFlag = yeatFlag;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getOverdueBeginTime() {
        return overdueBeginTime;
    }

    public void setOverdueBeginTime(Date overdueBeginTime) {
        this.overdueBeginTime = overdueBeginTime;
    }

    public BigDecimal getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(BigDecimal overdueRate) {
        this.overdueRate = overdueRate;
    }

    public String getInstruct() {
        return instruct;
    }

    public void setInstruct(String instruct) {
        this.instruct = instruct;
    }

    public String getExchangerPrice() {
        return exchangerPrice;
    }

    public void setExchangerPrice(String exchangerPrice) {
        this.exchangerPrice = exchangerPrice;
    }

    public String getMeteringChargingType() {
        return MeteringChargingType;
    }

    public void setMeteringChargingType(String meteringChargingType) {
        MeteringChargingType = meteringChargingType;
    }

    public String getApproveRes() {
        return approveRes;
    }

    public void setApproveRes(String approveRes) {
        this.approveRes = approveRes;
    }

    public String getApproveSerial() {
        return approveSerial;
    }

    public void setApproveSerial(String approveSerial) {
        this.approveSerial = approveSerial;
    }

    public String getApproveName() {
        return approveName;
    }

    public void setApproveName(String approveName) {
        this.approveName = approveName;
    }

    public Date getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(Date approveDate) {
        this.approveDate = approveDate;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public PriceDefine getOriginData() {
        return originData;
    }

    public void setOriginData(PriceDefine originData) {
        this.originData = originData;
    }

    public BigDecimal getPrePriceTmp() {
        return prePriceTmp;
    }

    public void setPrePriceTmp(BigDecimal prePriceTmp) {
        this.prePriceTmp = prePriceTmp;
    }

    public BigDecimal getAreaPriceTmp() {
        return areaPriceTmp;
    }

    public void setAreaPriceTmp(BigDecimal areaPriceTmp) {
        this.areaPriceTmp = areaPriceTmp;
    }

    public BigDecimal getHeatPriceTmp() {
        return heatPriceTmp;
    }

    public void setHeatPriceTmp(BigDecimal heatPriceTmp) {
        this.heatPriceTmp = heatPriceTmp;
    }

    public BigDecimal getExchangerPriceTmp() {
        return exchangerPriceTmp;
    }

    public void setExchangerPriceTmp(BigDecimal exchangerPriceTmp) {
        this.exchangerPriceTmp = exchangerPriceTmp;
    }

    /**
     * 热费热价名称

     */
    @NotNull(message = "热费热价名称不能为空")
    private String priceName;

    /**
     * 采暖季
     */
    @NotNull(message = "采暖季不能为空")
    private String annual;

    /**
     * 公司ＩＤ
     */
    @NotNull(message = "公司ＩＤ不能为空")
    private String companyId;

    /**
     * 预收单价 需加密
     */
    @NotNull(message = "预收单价不能为空")
    private String prePrice;

    /**
     * 面积价格 需加密
     */
    @NotNull(message = "面积价格不能为空")
    private String areaPrice;

    /**
     * 热量价格 需加密
     */
    @NotNull(message = "热量价格不能为空")
    private String heatPrice;

    /**
     * 是否当前采暖季：１当前采暖季
     */
    @NotNull(message = "是否当前采暖季不能为空")
    private Integer yeatFlag;

    /**
     * 备注
     */
    private String notes;

    /**
     * 滞纳金计算日期
     */

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date overdueBeginTime;

    /**
     * 滞纳金率
     */
    @Range(min=0,max=50000,message="range.bean.wage")
    private BigDecimal overdueRate;

    /**
     * 单价说明
     */
    private String instruct;

    /**
     * 换热器价格     需加密
     */
    @NotNull(message = "换热器价格不能为空")
    private String exchangerPrice;

/*计量费用计算:无、多退少补、多退少不补	*/
    @NotNull(message = "计量费用计算不能为空")
    private String MeteringChargingType;

   /*审批结果:通过、未通过*/
    private String approveRes;



    /*审核流水*/
    private String approveSerial;


    /*审核人姓名*/
    private String approveName;

    /*审核日期*/
    private Date approveDate;


    /*审核类型*/
    private String approveType;

    /*创建日期*/
    private   Date  createDate;

    /*创建人*/
    private   String  createUser;

    /*更新日期*/
    private   Date  updateDate;

    /*更新人*/
    private   String  updateUser;


    //未审核前的数据（用来返回给前端做判断）
    private PriceDefine  originData;



    private BigDecimal prePriceTmp;

    private BigDecimal areaPriceTmp;

    private BigDecimal heatPriceTmp;

    private BigDecimal exchangerPriceTmp;



}