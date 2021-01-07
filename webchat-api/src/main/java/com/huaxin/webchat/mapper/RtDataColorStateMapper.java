package com.huaxin.webchat.mapper;

import java.util.List;
import java.util.Map;

public interface RtDataColorStateMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    void saveOfUpdateState(Map<String,Object> params);

    void deleteRtDataColorState(Map<String,Object> params);
}
