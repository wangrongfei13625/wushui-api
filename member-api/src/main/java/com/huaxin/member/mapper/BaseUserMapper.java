package com.huaxin.member.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface BaseUserMapper {

    List<Map<String,Object>> findByUser(Map<String,Object> params);

    List<Map<String,Object>> findHoDepartment(Map<String,Object> params);


}
