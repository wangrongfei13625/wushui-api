package com.huaxin.webchat.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.webchat.mapper.EtlWarnHistoryMapper;
import com.huaxin.webchat.service.EtlWarnHistoryService;
import com.huaxin.webchat.unit.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EtlWarnHistoryServiceImpl implements EtlWarnHistoryService {

    @Autowired
    private EtlWarnHistoryMapper etlWarnHistoryMapper;


    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> dataLimitList =etlWarnHistoryMapper.findList(params);

        for(Map<String,Object> limitInfoMap:dataLimitList){
            Map<String,Object> map = new HashMap<>();
            map.put("historyId",limitInfoMap.get("history_id") == null ? null : limitInfoMap.get("history_id").toString());
            map.put("orgId",limitInfoMap.get("organization_id") == null ? null : limitInfoMap.get("organization_id").toString());
            map.put("dataId",limitInfoMap.get("data_id") == null ? null : limitInfoMap.get("data_id").toString());
            map.put("dataStateID",limitInfoMap.get("DATA_STATE_ID") == null ? null : limitInfoMap.get("DATA_STATE_ID").toString());
            map.put("exceed",limitInfoMap.get("EXCEED") == null ? null : limitInfoMap.get("EXCEED").toString());
            map.put("dataDate",limitInfoMap.get("DATA_DATE") == null ? null : limitInfoMap.get("DATA_DATE").toString());
            map.put("dataValue",limitInfoMap.get("DATA_VALUE") == null ? null : limitInfoMap.get("DATA_VALUE").toString());
            map.put("configId",limitInfoMap.get("CONFIG_ID") == null ? null : limitInfoMap.get("CONFIG_ID").toString());
            map.put("recordTime",limitInfoMap.get("RECORD_TIME") == null ? null : limitInfoMap.get("RECORD_TIME").toString());
            list.add(map);

        }
        return new PageInfo(list);
    }

    @Override
    public  List<Map<String,Object>> findOfById(Map<String,Object> params){

        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> dataLimitList =etlWarnHistoryMapper.findList(params);
        for(Map<String,Object> limitInfoMap:dataLimitList){
            Map<String,Object> map = new HashMap<>();
            map.put("historyId",limitInfoMap.get("history_id") == null ? null : limitInfoMap.get("history_id").toString());
            map.put("orgId",limitInfoMap.get("organization_id") == null ? null : limitInfoMap.get("organization_id").toString());
            map.put("dataId",limitInfoMap.get("data_id") == null ? null : limitInfoMap.get("data_id").toString());
            map.put("dataStateID",limitInfoMap.get("DATA_STATE_ID") == null ? null : limitInfoMap.get("DATA_STATE_ID").toString());
            map.put("exceed",limitInfoMap.get("EXCEED") == null ? null : limitInfoMap.get("EXCEED").toString());
            map.put("dataDate",limitInfoMap.get("DATA_DATE") == null ? null : limitInfoMap.get("DATA_DATE").toString());
            map.put("dataValue",limitInfoMap.get("DATA_VALUE") == null ? null : limitInfoMap.get("DATA_VALUE").toString());
            map.put("configId",limitInfoMap.get("CONFIG_ID") == null ? null : limitInfoMap.get("CONFIG_ID").toString());
            map.put("recordTime",limitInfoMap.get("RECORD_TIME") == null ? null : limitInfoMap.get("RECORD_TIME").toString());
            list.add(map);

        }
        return list;
    }
}
