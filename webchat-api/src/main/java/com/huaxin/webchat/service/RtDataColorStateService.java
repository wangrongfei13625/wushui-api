package com.huaxin.webchat.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface RtDataColorStateService {

    PageInfo findList(Map<String,Object> params);

    List<Map<String,Object>> findOfById(Map<String,Object> params);

    void saveOfUpdateState(Map<String,Object> params);

    void deleteRtDataColorState(Map<String,Object> params);

}
