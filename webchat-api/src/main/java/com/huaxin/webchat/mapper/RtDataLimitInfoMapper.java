package com.huaxin.webchat.mapper;

import java.util.List;
import java.util.Map;

public interface RtDataLimitInfoMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    void saveOfUpdateInfo(Map<String,Object> params);

    void deleteRtDataLimitInfo(Map<String, Object> map);

}
