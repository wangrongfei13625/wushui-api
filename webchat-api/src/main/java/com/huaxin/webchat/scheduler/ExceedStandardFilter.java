package com.huaxin.webchat.scheduler;


import com.huaxin.webchat.cache.DataFilterInfoCache;
import com.huaxin.webchat.cache.DataLimitInfoCache;
import com.huaxin.webchat.model.DataFilterInfoBean;
import com.huaxin.webchat.model.DataLimitInfoBean;
import com.huaxin.webchat.unit.DataLimitInfoComparator;
import com.huaxin.webchat.unit.ExpressionTimeFormat;
import com.huaxin.webchat.unit.Region;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ljj
 * Date: 14-12-12
 * Time: 上午11:51
 * To change this template use File | Settings | File Templates.
 */
public class ExceedStandardFilter {
    String exceed = null;
    String dataStateId = null;
    String value = null;
    String expression = null;
    private static final Log log = LogFactory.getLog(ExceedStandardFilter.class);

    public String isExceedOrFilter(String organizationId, String dataId, String dataValue, String dateTime){
        try{
            Map<String, Map<String,DataLimitInfoBean>> dataLimitInfoMap = DataLimitInfoCache.getInstance();
            Map<String,DataLimitInfoBean> map = dataLimitInfoMap.get(organizationId+"@"+dataId);
            if(map == null){
                map = dataLimitInfoMap.get("-1"+"@"+dataId);
            }
            if(map != null ){

                List<DataLimitInfoBean> limitInfoBeans = new ArrayList<DataLimitInfoBean>();
                limitInfoBeans.addAll(map.values());
                Collections.sort(limitInfoBeans,new DataLimitInfoComparator());

//                log.error("size:"+set.size());
                for(DataLimitInfoBean dataLimitInfoBean:limitInfoBeans){
                    dataLimitInfoBean.setActionTime(dataLimitInfoBean.getActionTime().substring(0,19));
                    dataLimitInfoBean.setFalseTime(dataLimitInfoBean.getFalseTime().substring(0,19));

                    if(dataLimitInfoBean.getActionTime().compareTo(dateTime) > 0 || dataLimitInfoBean.getFalseTime().compareTo(dateTime) < 0){
                        continue;
                    }
                    if(dataLimitInfoBean.getDataInaccuracy() == null){
                        dataLimitInfoBean.setDataInaccuracy("0");
                    }
                    double result = Double.parseDouble(dataValue)+ Double.parseDouble(dataLimitInfoBean.getDataInaccuracy());
                    boolean b = new Region().Check(result, dataLimitInfoBean.getExtendExpression());//值 是否在范围内

                    Boolean flag = false;
                    if(dataLimitInfoBean.getStartTime() != null && !dataLimitInfoBean.getStartTime().equals("")){
                        String startTime = new ExpressionTimeFormat().getTime(dateTime,dataLimitInfoBean.getStartTime());
                        String endTime = new ExpressionTimeFormat().getTime(dateTime,dataLimitInfoBean.getEndTime());
//                        log.error("start+end:"+startTime+","+endTime);
                        flag = dateTime.compareTo(startTime) >=0 && dateTime.compareTo(endTime) <=0 && b;
                    }else{
                        flag = b;
                    }
//                    log.error("flag:"+flag);
                    if(flag){
//                        log.error("找到对应的标准:"+organizationId+"---"+dataId+"----"+dataLimitInfoBean.getDataStateID()+"|"+dataLimitInfoBean.getActionTime()+"|"+dataLimitInfoBean.getFalseTime());
                        isExceed(organizationId,dataId,dataValue,dateTime, dataLimitInfoBean);
                    }
//                    log.error("is--exceed:"+exceed);
                    if(exceed != null){
                        break;
                    }
                }
            }
        }catch (Exception e){
            log.error("",e);
        }
        if(exceed == null){
            exceed = "0";
            dataStateId = "0";
        }
        if(value == null){
            value = dataValue;
        }
        return dataStateId+"@"+exceed+"@"+value+"@"+expression;
    }

    public void isExceed(String organizationId, String dataId, String dataValue, String dateTime, DataLimitInfoBean dataLimitInfoBean){
        dataStateId = dataLimitInfoBean.getDataStateID();
        expression = dataLimitInfoBean.getExtendExpression();
        List<DataFilterInfoBean> dataFilterInfoList = DataFilterInfoCache.getInstance();
        if(dataFilterInfoList.size() == 0){
            exceed = dataLimitInfoBean.getDataExceed();
        }else{
            if(dataLimitInfoBean.getDataExceed().equals("1")){
                for(DataFilterInfoBean dataFilterInfoBean:dataFilterInfoList){
                    if(dataFilterInfoBean.getOrganizationId().equals(organizationId) && dataFilterInfoBean.getDataId().equals(dataId)){
                        String filterStartTime = new ExpressionTimeFormat().getTime(dateTime,dataFilterInfoBean.getStartTime());
                        String filterEndTime = new ExpressionTimeFormat().getTime(dateTime,dataFilterInfoBean.getEndTime());
                        if (dateTime.compareTo(filterStartTime)>=0 && dateTime.compareTo(filterEndTime)<=0) {//时间符合
                            if (dataFilterInfoBean.getOldDataStateID().equals(dataStateId)) {
                                dataStateId = dataFilterInfoBean.getNewDataStateID();
                                String key = dataLimitInfoBean.getOrganizationId()+"@"+dataLimitInfoBean.getDataId()+"@"+dataStateId+"@"+dataLimitInfoBean.getActionTime()+"@"+dataLimitInfoBean.getStartTime();
                                Map<String,DataLimitInfoBean> dataLimitInfoMap = DataLimitInfoCache.getInstance().get(organizationId+"@"+dataId);
                                if(dataLimitInfoMap == null){
                                    dataLimitInfoMap = DataLimitInfoCache.getInstance().get("-1"+"@"+dataId);
                                }
                                if(dataLimitInfoMap != null){
                                    DataLimitInfoBean dataLimit = dataLimitInfoMap.get(key);
                                    if(dataLimit != null){
                                        value = new Region().Get(dataLimit.getExtendExpression());  //在区间内随机
                                        exceed = dataLimit.getDataExceed();
                                    }
                                }
                            }
                        }
                        if(exceed != null){
                            break;
                        }
                    }
                }
                if(exceed == null){
                    for(DataFilterInfoBean dataFilterInfoBean:dataFilterInfoList){
                        if(dataFilterInfoBean.getOrganizationId().equals("-1") && dataFilterInfoBean.getDataId().equals(dataId)){
                            String filterStartTime = new ExpressionTimeFormat().getTime(dateTime,dataFilterInfoBean.getStartTime());
                            String filterEndTime = new ExpressionTimeFormat().getTime(dateTime,dataFilterInfoBean.getEndTime());
                            if (dateTime.compareTo(filterStartTime)>=0 && dateTime.compareTo(filterEndTime)<=0) {//时间符合
                                if (dataFilterInfoBean.getOldDataStateID().equals(dataStateId)) {
                                    dataStateId = dataFilterInfoBean.getNewDataStateID();
                                    String key = dataLimitInfoBean.getOrganizationId()+"@"+dataLimitInfoBean.getDataId()+"@"+dataStateId+"@"+dataLimitInfoBean.getActionTime()+"@"+dataLimitInfoBean.getStartTime();
                                    Map<String,DataLimitInfoBean> dataLimitInfoMap = DataLimitInfoCache.getInstance().get(organizationId+"@"+dataId);
                                    if(dataLimitInfoMap == null){
                                        dataLimitInfoMap = DataLimitInfoCache.getInstance().get("-1"+"@"+dataId);
                                    }
                                    if(dataLimitInfoMap != null){
                                        DataLimitInfoBean dataLimit = dataLimitInfoMap.get(key);
                                        if(dataLimit != null){
                                            value = new Region().Get(dataLimit.getExtendExpression());  //在区间内随机
                                            exceed = dataLimit.getDataExceed();
                                        }
                                    }
                                }

                            }
                            if(exceed != null){
                                break;
                            }
                        }
                    }
                }
            }else{
                exceed = dataLimitInfoBean.getDataExceed();
            }
        }
    }

    public void init() {
        this.exceed = null;
        this.dataStateId = null;
        this.value = null;
        expression = null;
    }


}
