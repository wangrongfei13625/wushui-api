package com.huaxin.webchat.service.impl;



import com.huaxin.webchat.mapper.BaseUserMapper;
import com.huaxin.webchat.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaseUserServiceImpl implements BaseUserService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    public List<Map<String,Object>> findByUser(){
        return baseUserMapper.findByUser();
    }



}
