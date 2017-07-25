package com.eyun.jybfreightscan.product.entity;

/**
 * Created by Administrator on 2017/4/12.
 */

public class VehicleDispathForwardedState {
    // Fields

    private String vehDispNumber;
    private Integer vehDispForwardedState;
    private String remark;
    private Double locationLng;
    private Double locationLat;
    private Long lowerDestinationComCusID;
    private String lowerDestinationName;
    private String lowerDestinationAddress;
    private String lowerDestinationPhone;
    private Long createUserId;
    private Long createComBrId;
    private Long createComId;
    private String createIp;
    private String createTime;
    private Long stateID;

    public Long getStateID() {
        return stateID;
    }
    public void setStateID(Long stateID) {
        this.stateID = stateID;
    }

    public String getVehDispNumber() {
        return vehDispNumber;
    }
    public void setVehDispNumber(String vehDispNumber) {
        this.vehDispNumber = vehDispNumber;
    }
    public Integer getVehDispForwardedState() {
        return vehDispForwardedState;
    }
    public void setVehDispForwardedState(Integer vehDispForwardedState) {
        this.vehDispForwardedState = vehDispForwardedState;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getLocationLng() {
        return locationLng;
    }
    public void setLocationLng(Double locationLng) {
        this.locationLng = locationLng;
    }
    public Double getLocationLat() {
        return locationLat;
    }
    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }
    public String getLowerDestinationPhone() {
        return lowerDestinationPhone;
    }
    public void setLowerDestinationPhone(String lowerDestinationPhone) {
        this.lowerDestinationPhone = lowerDestinationPhone;
    }
    public String getLowerDestinationName() {
        return lowerDestinationName;
    }
    public void setLowerDestinationName(String lowerDestinationName) {
        this.lowerDestinationName = lowerDestinationName;
    }
    public Long getLowerDestinationComCusID() {
        return lowerDestinationComCusID;
    }
    public void setLowerDestinationComCusID(Long lowerDestinationComCusID) {
        this.lowerDestinationComCusID = lowerDestinationComCusID;
    }
    public String getLowerDestinationAddress() {
        return lowerDestinationAddress;
    }
    public void setLowerDestinationAddress(String lowerDestinationAddress) {
        this.lowerDestinationAddress = lowerDestinationAddress;
    }
    public Long getCreateUserId() {
        return createUserId;
    }
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    public Long getCreateComBrId() {
        return createComBrId;
    }
    public void setCreateComBrId(Long createComBrId) {
        this.createComBrId = createComBrId;
    }
    public Long getCreateComId() {
        return createComId;
    }
    public void setCreateComId(Long createComId) {
        this.createComId = createComId;
    }
    public String getCreateIp() {
        return createIp;
    }
    public void setCreateIp(String createIp) {
        this.createIp = createIp;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
