package com.huaxin.webchat.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface EtlWarnConfigService {

    PageInfo findList(Map<String, Object> map);

    List<Map<String,Object>> findById(Map<String, Object> map);

    void saveOfUpdateConfig(Map<String, Object> map);

    void deleteEtlWarnConfig(Map<String, Object> map);

}
