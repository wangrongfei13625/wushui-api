package com.huaxin.webchat.model;

/**
 * Created with IntelliJ IDEA.
 * User: ljj
 * Date: 14-12-12
 * Time: 上午10:03
 * To change this template use File | Settings | File Templates.
 */
public class DataLimitInfoBean {
    private String organizationId ;    //组织ID
    private String dataId;             //数据ID
    private String dataInaccuracy;  //数据误差偏移量
    private String startTime;       //开始执行时间
    private String endTime ;        //结束执行时间
    private String dataStateID;        //数据状态
    private String extendExpression ;   //数据范围(公式)
    private String dataExceed;      //数据状态类型 0正常(未超标)1异常(超标)
    private String actionTime;
    private String falseTime;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataInaccuracy() {
        return dataInaccuracy;
    }

    public void setDataInaccuracy(String dataInaccuracy) {
        this.dataInaccuracy = dataInaccuracy;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDataStateID() {
        return dataStateID;
    }

    public void setDataStateID(String dataStateID) {
        this.dataStateID = dataStateID;
    }

    public String getExtendExpression() {
        return extendExpression;
    }

    public void setExtendExpression(String extendExpression) {
        this.extendExpression = extendExpression;
    }

    public String getDataExceed() {
        return dataExceed;
    }

    public void setDataExceed(String dataExceed) {
        this.dataExceed = dataExceed;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getFalseTime() {
        return falseTime;
    }

    public void setFalseTime(String falseTime) {
        this.falseTime = falseTime;
    }
}
