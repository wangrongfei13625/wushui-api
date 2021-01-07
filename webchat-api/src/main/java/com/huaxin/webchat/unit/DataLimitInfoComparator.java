package com.huaxin.webchat.unit;

import com.huaxin.webchat.model.DataLimitInfoBean;

import java.util.Comparator;

/**
 * Created by Administrator on 2015/8/13.
 */
public class DataLimitInfoComparator implements Comparator<DataLimitInfoBean> {
    @Override
    public int compare(DataLimitInfoBean o1, DataLimitInfoBean o2) {
        if(o2.getActionTime().equals(o1.getActionTime())){
            if(o2.getFalseTime().equals(o1.getFalseTime())){
                return o2.getDataStateID().compareTo(o1.getDataStateID());
            }
            return o1.getFalseTime().compareTo(o2.getFalseTime());
        }
        return o2.getActionTime().compareTo(o1.getActionTime()) ;
    }
}
