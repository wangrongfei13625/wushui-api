package com.huaxin.webchat.mapper;

import java.util.List;
import java.util.Map;

public interface EtlWarnHistoryMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    void saveWeChart(Map<String,Object> params);

}
