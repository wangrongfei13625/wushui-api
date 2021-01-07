package com.huaxin.webchat.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface HxConfigMapper {

    List<Map<String,Object>> findByConfigId(@Param(value = "configId") String configId);

}
