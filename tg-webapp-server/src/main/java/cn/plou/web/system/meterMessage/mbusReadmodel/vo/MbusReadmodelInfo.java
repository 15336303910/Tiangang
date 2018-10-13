package cn.plou.web.system.meterMessage.mbusReadmodel.vo;

import cn.plou.web.system.meterMessage.mbusReadmodel.entity.MbusReadmodel;

public class MbusReadmodelInfo extends MbusReadmodel {
    private String companyName;

    private String consumerName;

    private String consumerId;

    private String factoryName;

    private String channelstateName;

    private String isvirtualName;

    private String mbusPosition;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getChannelstateName() {
        return channelstateName;
    }

    public void setChannelstateName(String channelstateName) {
        this.channelstateName = channelstateName;
    }

    public String getIsvirtualName() {
        return isvirtualName;
    }

    public void setIsvirtualName(String isvirtualName) {
        this.isvirtualName = isvirtualName;
    }

    public String getMbusPosition() {
        return mbusPosition;
    }

    public void setMbusPosition(String mbusPosition) {
        this.mbusPosition = mbusPosition;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }
}
