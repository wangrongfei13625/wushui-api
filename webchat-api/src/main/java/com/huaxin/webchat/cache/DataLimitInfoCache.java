package com.huaxin.webchat.cache;


import com.huaxin.webchat.mapper.DataLimitInfoMapper;
import com.huaxin.webchat.model.DataLimitInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ljj
 * Date: 14-12-12
 * Time: 上午10:05
 * To change this template use File | Settings | File Templates.
 */
@Component
public class DataLimitInfoCache implements CommandLineRunner {

    @Autowired
    private DataLimitInfoMapper dataLimitInfoMapper;

    private static Map<String, Map<String, DataLimitInfoBean>> ourInstance = new HashMap<String, Map<String,DataLimitInfoBean>>();

    public static Map<String, Map<String,DataLimitInfoBean>> getInstance(){
        return ourInstance;
    }

    @Override
    public void run(String... args) throws Exception{
        ourInstance.clear();
        List<Map<String, Object>> dataLimitInfoList = dataLimitInfoMapper.findAll();
        for(Map<String, Object> limitInfoMap: dataLimitInfoList){
            String organizationId = limitInfoMap.get("organizationId") == null ? null : limitInfoMap.get("organizationId").toString();
            String dataId = limitInfoMap.get("dataId") == null ? null : limitInfoMap.get("dataId").toString();
            String dataInaccuracy = limitInfoMap.get("dataInaccuracy") == null ? null : limitInfoMap.get("dataInaccuracy").toString();
            String startTime = limitInfoMap.get("startTime") == null ? null : limitInfoMap.get("startTime").toString();
            String endTime = limitInfoMap.get("endTime") == null ? null : limitInfoMap.get("endTime").toString();
            String dataStateID =  limitInfoMap.get("dataStateID") == null ? null : limitInfoMap.get("dataStateID").toString();
            String extendExpression = limitInfoMap.get("extendExpression") == null ? null : limitInfoMap.get("extendExpression").toString();
            String dataExceed = limitInfoMap.get("dataExceed") == null ? null : limitInfoMap.get("dataExceed").toString();
            String actionTime = limitInfoMap.get("actionTime") == null ? null : limitInfoMap.get("actionTime").toString();
            String falseTime = limitInfoMap.get("false_time") == null ? null : limitInfoMap.get("false_time").toString();
            DataLimitInfoBean dataLimitInfoBean = new DataLimitInfoBean();
            dataLimitInfoBean.setOrganizationId(organizationId);
            dataLimitInfoBean.setDataId(dataId);
            dataLimitInfoBean.setDataInaccuracy(dataInaccuracy);
            dataLimitInfoBean.setStartTime(startTime);
            dataLimitInfoBean.setEndTime(endTime);
            dataLimitInfoBean.setDataStateID(dataStateID);
            dataLimitInfoBean.setExtendExpression(extendExpression);
            dataLimitInfoBean.setDataExceed(dataExceed);
            dataLimitInfoBean.setActionTime(actionTime);
            dataLimitInfoBean.setFalseTime(falseTime);
            String key = organizationId+"@"+dataId+"@"+dataStateID+"@"+actionTime+"@"+startTime;

            String mapKey = organizationId+"@"+dataId;
            Map<String,DataLimitInfoBean> map = ourInstance.get(mapKey);
            if(map != null){
                map.put(key,dataLimitInfoBean);
            }else{
                Map<String,DataLimitInfoBean> newMap = new HashMap<String,DataLimitInfoBean>();
                newMap.put(key,dataLimitInfoBean);
                ourInstance.put(mapKey,newMap);
            }


        }
    }
}
