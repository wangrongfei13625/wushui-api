package com.huaxin.member.service.impl;



import com.huaxin.member.mapper.BaseUserMapper;
import com.huaxin.member.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseUserServiceImpl implements BaseUserService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Override
    public List<Map<String,Object>> findByUser(Map<String,Object> params){

        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> userList = baseUserMapper.findByUser(params);
        for(Map<String,Object> user: userList){
            Map<String,Object> map = new HashMap<>();

            map.put("userId",user.get("id")==null?null:user.get("id").toString());
            map.put("loginName",user.get("CODE")==null?null:user.get("CODE").toString());
            map.put("userName",user.get("NAME")==null?null:user.get("NAME").toString());
            map.put("description",user.get("DESCRIPTION")==null?null:user.get("DESCRIPTION").toString());
            map.put("personTell",user.get("PERSON_TELL")==null?null:user.get("PERSON_TELL").toString());
            map.put("sex",user.get("SEX")==null?null:user.get("SEX").toString());
            map.put("telephone",user.get("TELEPHONE")==null?null:user.get("TELEPHONE").toString());
            map.put("degree",user.get("DEGREE")==null?null:user.get("DEGREE").toString());
            map.put("orgId",user.get("orgId")==null?null:user.get("orgId").toString());
            map.put("orgName",user.get("orgName")==null?null:user.get("orgName").toString());
            list.add(map);

        }

        return list;
    }

    @Override
    public List<Map<String,Object>> findHoDepartment(Map<String,Object> params){

        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> departMentList = baseUserMapper.findHoDepartment(params);
        for(Map<String,Object> departMent: departMentList){
            Map<String,Object> map = new HashMap<>();

            map.put("orgId",departMent.get("id")==null?null:departMent.get("id").toString());
            map.put("code",departMent.get("CODE")==null?null:departMent.get("CODE").toString());
            map.put("orgName",departMent.get("NAME")==null?null:departMent.get("NAME").toString());
            map.put("description",departMent.get("DESCRIPTION")==null?null:departMent.get("DESCRIPTION").toString());
            map.put("companyCode",departMent.get("COMPANY_CODE")==null?null:departMent.get("COMPANY_CODE").toString());
            map.put("pid",departMent.get("pid")==null?null:departMent.get("pid").toString());
            list.add(map);

        }


        return list;
    }



}
