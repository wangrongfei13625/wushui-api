package com.huaxin.member.service.impl;

import com.huaxin.member.mapper.MetersTagDataValueMapper;
import com.huaxin.member.service.MetersTagDataValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MetersTagDataValueServiceImpl implements MetersTagDataValueService {

    @Autowired
    private MetersTagDataValueMapper metersTagDataValueMapper;

    public List<Map<String,Object>> findListOfDataId(Map<String,Object> params){


        List<Map<String,Object>> dataList = metersTagDataValueMapper.findListOfDataId(params);


        return dataList;

    }



}
