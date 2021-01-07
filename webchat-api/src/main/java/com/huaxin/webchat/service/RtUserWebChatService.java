package com.huaxin.webchat.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface RtUserWebChatService {

    PageInfo findList(Map<String,Object> params);

    List<Map<String,Object>> findOfById(Map<String,Object> params);

    void saveOfUpdateWebChat(Map<String,Object> params);

    void deleteWebChat(Map<String, Object> map);

}
