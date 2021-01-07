package com.huaxin.webchat.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface BaseUserMapper {

    List<Map<String,Object>> findByUser();


}
