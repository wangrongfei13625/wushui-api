package com.huaxin.webchat.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="leakageMessage")
public class LeakageMessage
{
    private String id;
    private String linkMan;
    private String linkPhone;
    private String linkAddress;
    private String areaName;
    private String reportMemo;
    private String mediaId;
    private Date createTime;
    private String receiveCode;
    private String applyType;
    private String status;
    private String cxbh;
    private String openid;

    @Id
    @Column(name="id")
    public String getId()
    {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name="linkMan")
    public String getLinkMan() {
        return this.linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }
    @Column(name="linkPhone")
    public String getLinkPhone() {
        return this.linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }
    @Column(name="linkAddress")
    public String getLinkAddress() {
        return this.linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }
    @Column(name="areaName")
    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    @Column(name="reportMemo")
    public String getReportMemo() {
        return this.reportMemo;
    }

    public void setReportMemo(String reportMemo) {
        this.reportMemo = reportMemo;
    }
    @Column(name="mediaId")
    public String getMediaId() {
        return this.mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
    @Column(name="createTime")
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    @Column(name="receiveCode")
    public String getReceiveCode() {
        return this.receiveCode;
    }

    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }
    @Column(name="applyType")
    public String getApplyType() {
        return this.applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }
    @Column(name="status")
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Column(name="cxbh")
    public String getCxbh() {
        return this.cxbh;
    }

    public void setCxbh(String cxbh) {
        this.cxbh = cxbh;
    }
    @Column(name="openid")
    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}