package com.huaxin.webchat.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.webchat.mapper.RtDataLimitInfoMapper;
import com.huaxin.webchat.service.RtDataLimitInfoService;
import com.huaxin.webchat.unit.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RtDataLimitInfoServiceImpl implements RtDataLimitInfoService {

    @Autowired
    private RtDataLimitInfoMapper rtDataLimitInfoMapper;

    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> dataLimitList =rtDataLimitInfoMapper.findList(params);
        for(Map<String,Object> limitInfoMap:dataLimitList){
            Map<String,Object> map = new HashMap<>();
            map.put("limitInfoId",limitInfoMap.get("LIMITINFOID") == null ? null : limitInfoMap.get("LIMITINFOID").toString());
            map.put("orgId",limitInfoMap.get("ORGANIZATIONID") == null ? null : limitInfoMap.get("ORGANIZATIONID").toString());
            map.put("displayExpression",limitInfoMap.get("DISPLAYEXPRESSION") == null ? null : limitInfoMap.get("DISPLAYEXPRESSION").toString());
            map.put("dataId",limitInfoMap.get("DATAID") == null ? null : limitInfoMap.get("DATAID").toString());
            map.put("dataInaccuracy",limitInfoMap.get("DATAINACCURACY") == null ? null : limitInfoMap.get("DATAINACCURACY").toString());
            map.put("startTime",limitInfoMap.get("STARTTIME") == null ? null : limitInfoMap.get("STARTTIME").toString());
            map.put("endTime",limitInfoMap.get("ENDTIME") == null ? null : limitInfoMap.get("ENDTIME").toString());
            map.put("dataStateID",limitInfoMap.get("DATASTATEID") == null ? null : limitInfoMap.get("DATASTATEID").toString());
            map.put("extendExpression",limitInfoMap.get("EXTENDEXPRESSION") == null ? null : limitInfoMap.get("EXTENDEXPRESSION").toString());
            map.put("dataExceed",limitInfoMap.get("dataExceed") == null ? null : limitInfoMap.get("dataExceed").toString());
            map.put("actionTime",limitInfoMap.get("ACTIONTIME") == null ? null : limitInfoMap.get("ACTIONTIME").toString());
            map.put("falseTime",limitInfoMap.get("FALSE_TIME") == null ? null : limitInfoMap.get("FALSE_TIME").toString());
            map.put("dataName",limitInfoMap.get("DATA_NAME") == null ? null : limitInfoMap.get("DATA_NAME").toString());
            list.add(map);

        }
        return new PageInfo(list);
    }

    @Override
    public  List<Map<String,Object>> findOfById(Map<String,Object> params){

        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> dataLimitList =rtDataLimitInfoMapper.findList(params);
        for(Map<String,Object> limitInfoMap:dataLimitList){
            Map<String,Object> map = new HashMap<>();
            map.put("limitInfoId",limitInfoMap.get("LIMITINFOID") == null ? null : limitInfoMap.get("LIMITINFOID").toString());
            map.put("orgId",limitInfoMap.get("ORGANIZATIONID") == null ? null : limitInfoMap.get("ORGANIZATIONID").toString());
            map.put("displayExpression",limitInfoMap.get("DISPLAYEXPRESSION") == null ? null : limitInfoMap.get("DISPLAYEXPRESSION").toString());
            map.put("dataId",limitInfoMap.get("DATAID") == null ? null : limitInfoMap.get("DATAID").toString());
            map.put("dataInaccuracy",limitInfoMap.get("DATAINACCURACY") == null ? null : limitInfoMap.get("DATAINACCURACY").toString());
            map.put("startTime",limitInfoMap.get("STARTTIME") == null ? null : limitInfoMap.get("STARTTIME").toString());
            map.put("endTime",limitInfoMap.get("ENDTIME") == null ? null : limitInfoMap.get("ENDTIME").toString());
            map.put("dataStateID",limitInfoMap.get("DATASTATEID") == null ? null : limitInfoMap.get("DATASTATEID").toString());
            map.put("extendExpression",limitInfoMap.get("EXTENDEXPRESSION") == null ? null : limitInfoMap.get("EXTENDEXPRESSION").toString());
            map.put("dataExceed",limitInfoMap.get("dataExceed") == null ? null : limitInfoMap.get("dataExceed").toString());
            map.put("actionTime",limitInfoMap.get("ACTIONTIME") == null ? null : limitInfoMap.get("ACTIONTIME").toString());
            map.put("falseTime",limitInfoMap.get("FALSE_TIME") == null ? null : limitInfoMap.get("FALSE_TIME").toString());
            map.put("dataName",limitInfoMap.get("DATA_NAME") == null ? null : limitInfoMap.get("DATA_NAME").toString());
            list.add(map);

        }
        return list;
    }

    @Override
    public void saveOfUpdateInfo(Map<String,Object> params){

        rtDataLimitInfoMapper.saveOfUpdateInfo(params);
    }

    @Override
    public  void deleteRtDataLimitInfo(Map<String, Object> map){

        if(map.get("ids")!=null && map.get("ids")!=""){
            String ids =map.get("ids").toString();
            String[] id = ids.split(",");
            map.put("ids",id);
        }

        rtDataLimitInfoMapper.deleteRtDataLimitInfo(map);
    }

}
