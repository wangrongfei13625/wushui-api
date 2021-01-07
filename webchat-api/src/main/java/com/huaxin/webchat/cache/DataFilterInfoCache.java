package com.huaxin.webchat.cache;

import com.huaxin.webchat.mapper.DataFilterInfoMapper;
import com.huaxin.webchat.model.DataFilterInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ljj
 * Date: 14-12-12
 * Time: 上午10:30
 * To change this template use File | Settings | File Templates.
 */
public class DataFilterInfoCache implements CommandLineRunner {

    @Autowired
    private DataFilterInfoMapper dataFilterInfoMapper;

    private static List<DataFilterInfoBean> ourInstance = new ArrayList<DataFilterInfoBean>();

    public static List<DataFilterInfoBean> getInstance(){
        return ourInstance;
    }

    @Override
    public void run(String... args) throws Exception{
        List<Map<String, Object>> dataFilterInfoList = dataFilterInfoMapper.findAll();
        ourInstance.clear();
        for(Map<String, Object> filterInfoMap: dataFilterInfoList){
            String organizationId = filterInfoMap.get("ORGANIZATIONID") == null ? null : filterInfoMap.get("ORGANIZATIONID").toString();
            String dataId = filterInfoMap.get("DATAID") == null ? null : filterInfoMap.get("DATAID").toString();
            String oldDataStateID = filterInfoMap.get("OLDDATASTATEID") == null ? null : filterInfoMap.get("OLDDATASTATEID").toString();
            String newDataStateID = filterInfoMap.get("NEWDATASTATEID") == null ? null : filterInfoMap.get("NEWDATASTATEID").toString();
            String startTime = filterInfoMap.get("STARTTIME") == null ? null : filterInfoMap.get("STARTTIME").toString();
            String endTime = filterInfoMap.get("ENDTIME") == null ? null : filterInfoMap.get("ENDTIME").toString();

            DataFilterInfoBean dataFilterInfoBean = new DataFilterInfoBean();
            dataFilterInfoBean.setOrganizationId(organizationId);
            dataFilterInfoBean.setDataId(dataId);
            dataFilterInfoBean.setOldDataStateID(oldDataStateID);
            dataFilterInfoBean.setNewDataStateID(newDataStateID);
            dataFilterInfoBean.setStartTime(startTime);
            dataFilterInfoBean.setEndTime(endTime);

            ourInstance.add(dataFilterInfoBean);
        }
    }
}
