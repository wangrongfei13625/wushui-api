package com.huaxin.webchat.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface EtlWarnHistoryService {

    PageInfo findList(Map<String,Object> params);

    List<Map<String,Object>> findOfById(Map<String,Object> params);

}
