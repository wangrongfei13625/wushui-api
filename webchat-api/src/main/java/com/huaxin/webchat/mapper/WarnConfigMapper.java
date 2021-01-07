package com.huaxin.webchat.mapper;



import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WarnConfigMapper {

    List<Map<String,Object>> findByOrgIdAndDataId(Map<String,Object> params);

    List<Map<String,Object>> findPointInfo(Map<String,Object> params);

    List<Map<String,Object>> findDataName(Map<String,Object> params);

    List<Map<String,Object>> findOrganizationName(Map<String,Object> params);

    List<Map<String,Object>> findRealDataColorStateName(Map<String,Object> params);

    List<Integer> findUserIdByOrgId(@Param(value = "orgIds") String orgIds);

    List<Map<String,Object>> findUsersByRoleId(@Param(value = "role") Integer role);


    List<Map<String,Object>> findUserId(@Param(value = "id") String id);

    List<Map<String,Object>> findWebChatOfUserId(@Param(value = "id") String id);






}
