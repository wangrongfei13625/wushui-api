package com.huaxin.webchat.mapper;

import java.util.List;
import java.util.Map;

public interface LeakageMapper {

    List<Map<String,Object>> findById(Map<String,Object> map);

    List<Map<String,Object>> updateById(Map<String,Object> map);


}
