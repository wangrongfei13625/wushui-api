package com.huaxin.webchat.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface RtDataLimitInfoService {

    PageInfo findList(Map<String,Object> params);

    List<Map<String,Object>> findOfById(Map<String,Object> params);

    void saveOfUpdateInfo(Map<String,Object> params);

    void deleteRtDataLimitInfo(Map<String, Object> map);

}
