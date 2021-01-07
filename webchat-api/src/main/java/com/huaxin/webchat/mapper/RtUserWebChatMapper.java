package com.huaxin.webchat.mapper;

import java.util.List;
import java.util.Map;

public interface RtUserWebChatMapper {

    List<Map<String,Object>> findList(Map<String,Object> params);

    void saveOfUpdateWebChat(Map<String,Object> params);

    void deleteWebChat(Map<String,Object> params);
}
