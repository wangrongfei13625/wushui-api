package com.huaxin.member.service;

import java.util.List;
import java.util.Map;

public interface BaseUserService {


    List<Map<String,Object>> findByUser(Map<String,Object> params);

    List<Map<String,Object>> findHoDepartment(Map<String,Object> params);

}
