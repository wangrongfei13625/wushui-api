package com.huaxin.member.service.impl;

import com.huaxin.member.mapper.MetersTagDataInfoMapper;
import com.huaxin.member.service.MetersTagDataInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetersTagDataInfoServiceImpl implements MetersTagDataInfoService {

    @Autowired
    private MetersTagDataInfoMapper metersTagDataInfoMapper;

    public List<Map<String,Object>> findList(Map<String,Object> params){
        List<Map<String,Object>> list = new ArrayList<>();

        List<Map<String,Object>> dataList = metersTagDataInfoMapper.findList(params);
        for(Map<String,Object> dataMap : dataList){
            Map<String,Object> map = new HashMap<>();

            map.put("id",dataMap.get("ID")==null?null:dataMap.get("ID").toString());
            map.put("dataId",dataMap.get("dataId")==null?null:dataMap.get("dataId").toString());
            map.put("dataName",dataMap.get("dataName")==null?null:dataMap.get("dataName").toString());
            map.put("areaId",dataMap.get("AREA_ID")==null?null:dataMap.get("AREA_ID").toString());
            map.put("tagName",dataMap.get("tagName")==null?null:dataMap.get("tagName").toString());
            map.put("remark",dataMap.get("REMARK")==null?null:dataMap.get("REMARK").toString());

            list.add(map);
        }

        return list;
    }

}
