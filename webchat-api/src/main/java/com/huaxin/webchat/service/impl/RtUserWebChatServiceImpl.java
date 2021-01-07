package com.huaxin.webchat.service.impl;

import com.github.pagehelper.PageInfo;
import com.huaxin.webchat.mapper.RtUserWebChatMapper;
import com.huaxin.webchat.service.RtUserWebChatService;
import com.huaxin.webchat.unit.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RtUserWebChatServiceImpl implements RtUserWebChatService {

    @Autowired
    private RtUserWebChatMapper rtUserWebChatMapper;


    @Override
    public PageInfo findList(Map<String,Object> params){
        PageUtils.initPage(params);
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> webChatList =rtUserWebChatMapper.findList(params);
        for(Map<String,Object> webChatMap:webChatList){
            Map<String,Object> map = new HashMap<>();

            map.put("id",webChatMap.get("id") == null ? null : webChatMap.get("id").toString());
            map.put("userId",webChatMap.get("user_id") == null ? null : webChatMap.get("user_id").toString());
            map.put("userName",webChatMap.get("user_name") == null ? null : webChatMap.get("user_name").toString());
            map.put("webChatNum",webChatMap.get("webchat_num") == null ? null : webChatMap.get("webchat_num").toString());
            map.put("webChatName",webChatMap.get("webchat_name") == null ? null : webChatMap.get("webchat_name").toString());
            map.put("createTime",webChatMap.get("create_time") == null ? null : webChatMap.get("create_time").toString());
            list.add(map);

        }
        return new PageInfo(list);
    }


    @Override
    public  List<Map<String,Object>> findOfById(Map<String,Object> params){

        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> webChatList =rtUserWebChatMapper.findList(params);
        for(Map<String,Object> webChatMap:webChatList){
            Map<String,Object> map = new HashMap<>();

            map.put("id",webChatMap.get("id") == null ? null : webChatMap.get("id").toString());
            map.put("userId",webChatMap.get("user_id") == null ? null : webChatMap.get("user_id").toString());
            map.put("userName",webChatMap.get("user_name") == null ? null : webChatMap.get("user_name").toString());
            map.put("webChatNum",webChatMap.get("webchat_num") == null ? null : webChatMap.get("webchat_num").toString());
            map.put("webChatName",webChatMap.get("webchat_name") == null ? null : webChatMap.get("webchat_name").toString());
            map.put("createTime",webChatMap.get("create_time") == null ? null : webChatMap.get("create_time").toString());
            list.add(map);

        }
        return list;
    }

    @Override
    public void saveOfUpdateWebChat(Map<String,Object> params){

        rtUserWebChatMapper.saveOfUpdateWebChat(params);
    }

    @Override
    public  void deleteWebChat(Map<String, Object> map){

        if(map.get("ids")!=null && map.get("ids")!=""){
            String ids =map.get("ids").toString();
            String[] id = ids.split(",");
            map.put("ids",id);
        }

        rtUserWebChatMapper.deleteWebChat(map);
    }

}
