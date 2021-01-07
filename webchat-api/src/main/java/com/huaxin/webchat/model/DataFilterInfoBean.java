package com.huaxin.webchat.model;

/**
 * Created with IntelliJ IDEA.
 * User: ljj
 * Date: 14-12-12
 * Time: 上午10:04
 * To change this template use File | Settings | File Templates.
 */
public class DataFilterInfoBean {
    private String organizationId;
    private String dataId;
    private String oldDataStateID;
    private String newDataStateID;
    private String startTime;
    private String endTime;

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

    public String getOldDataStateID() {
        return oldDataStateID;
    }

    public void setOldDataStateID(String oldDataStateID) {
        this.oldDataStateID = oldDataStateID;
    }

    public String getNewDataStateID() {
        return newDataStateID;
    }

    public void setNewDataStateID(String newDataStateID) {
        this.newDataStateID = newDataStateID;
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
}
