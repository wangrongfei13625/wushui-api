package com.huaxin.webchat.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.webchat.mapper.EtlWarnConfigMapper;
import com.huaxin.webchat.service.EtlWarnConfigService;
import com.huaxin.webchat.unit.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EtlWarnConfigServiceImpl implements EtlWarnConfigService {

    @Autowired
    private EtlWarnConfigMapper etlWarnConfigMapper;

    @Override
    public PageInfo findList(Map<String, Object> map) {
        PageUtils.initPage(map);
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> warnList = etlWarnConfigMapper.findById(map);
        for (Map<String,Object> params: warnList) {
            Map<String,Object> param = new HashMap<>();
            param.put("configId",params.get("CONFIG_ID"));
            param.put("dataId",params.get("DATA_ID"));
            param.put("dataName",params.get("DATA_NAME"));
            param.put("orgId",params.get("ORGANIZATION_ID"));
            param.put("orgName",params.get("ORGANIZATION_NAME"));
            param.put("warnType",params.get("WARN_TYPE"));
            param.put("objectId",params.get("OBJECT_ID"));
            param.put("msgContent",params.get("MSG_CONTENT"));
            list.add(param);
        }

        return new PageInfo(list);

    }

    @Override
    public List<Map<String,Object>> findById(Map<String, Object> map) {
        //PageUtils.initPage(map);
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> warnList = etlWarnConfigMapper.findById(map);
        for (Map<String,Object> params: warnList) {
            Map<String,Object> param = new HashMap<>();
            param.put("configId",params.get("CONFIG_ID"));
            param.put("dataId",params.get("DATA_ID"));
            param.put("dataName",params.get("DATA_NAME"));
            param.put("orgId",params.get("ORGANIZATION_ID"));
            param.put("orgName",params.get("ORGANIZATION_NAME"));
            param.put("warnType",params.get("WARN_TYPE"));
            param.put("objectId",params.get("OBJECT_ID"));
            param.put("msgContent",params.get("MSG_CONTENT"));
            list.add(param);
        }

        return list;

    }

    @Override
    public  void saveOfUpdateConfig(Map<String, Object> map){
        etlWarnConfigMapper.saveOfUpdateConfig(map);
    }


    @Override
    public  void deleteEtlWarnConfig(Map<String, Object> map){

        if(map.get("ids")!=null && map.get("ids")!=""){
            String ids =map.get("ids").toString();
            String[] id = ids.split(",");
            map.put("ids",id);
        }

        etlWarnConfigMapper.deleteEtlWarnConfig(map);
    }

}
