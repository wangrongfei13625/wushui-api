package com.huaxin.webchat.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.webchat.mapper.RtDataColorStateMapper;
import com.huaxin.webchat.service.RtDataColorStateService;
import com.huaxin.webchat.unit.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RtDataColorStateServiceImpl implements RtDataColorStateService {

    @Autowired
    private RtDataColorStateMapper rtDataColorStateMapper;

    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> stateList = rtDataColorStateMapper.findList(params);
        for(Map<String,Object> stateMap:stateList){
            Map<String,Object> map = new HashMap<>();
            map.put("dataStateId",stateMap.get("DATASTATEID")==null?null:stateMap.get("DATASTATEID").toString());
            map.put("dataStateName",stateMap.get("DATASTATENAME")==null?null:stateMap.get("DATASTATENAME").toString());
            map.put("dataType",stateMap.get("DATATYPE")==null?null:stateMap.get("DATATYPE").toString());
            map.put("dataExceed",stateMap.get("DATAEXCEED")==null?null:stateMap.get("DATAEXCEED").toString());
            map.put("dataStateColor",stateMap.get("DATASTATECOLOR")==null?null:stateMap.get("DATASTATECOLOR").toString());
            map.put("dataStateMark",stateMap.get("DATASTATEMARK")==null?null:stateMap.get("DATASTATEMARK").toString());
            list.add(map);
        }

        return new PageInfo(list);
    }

    @Override
    public List<Map<String,Object>> findOfById(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> stateList = rtDataColorStateMapper.findList(params);
        for(Map<String,Object> stateMap:stateList){
            Map<String,Object> map = new HashMap<>();
            map.put("dataStateId",stateMap.get("DATASTATEID")==null?null:stateMap.get("DATASTATEID").toString());
            map.put("dataStateName",stateMap.get("DATASTATENAME")==null?null:stateMap.get("DATASTATENAME").toString());
            map.put("dataType",stateMap.get("DATATYPE")==null?null:stateMap.get("DATATYPE").toString());
            map.put("dataExceed",stateMap.get("DATAEXCEED")==null?null:stateMap.get("DATAEXCEED").toString());
            map.put("dataStateColor",stateMap.get("DATASTATECOLOR")==null?null:stateMap.get("DATASTATECOLOR").toString());
            map.put("dataStateMark",stateMap.get("DATASTATEMARK")==null?null:stateMap.get("DATASTATEMARK").toString());
            list.add(map);
        }

        return list;
    }

    @Override
    public void saveOfUpdateState(Map<String,Object> params){

        rtDataColorStateMapper.saveOfUpdateState(params);
    }

    @Override
    public  void deleteRtDataColorState(Map<String, Object> map){

        if(map.get("ids")!=null && map.get("ids")!=""){
            String ids =map.get("ids").toString();
            String[] id = ids.split(",");
            map.put("ids",id);
        }

        rtDataColorStateMapper.deleteRtDataColorState(map);
    }

}
