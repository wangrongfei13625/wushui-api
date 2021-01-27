package com.huaxin.webchat.fegin;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "member" ,url = "${feign.client.url}")
public interface MemberInfoFeign {

    /**
     * 获取点位数据
     * @param params
     * @return
     */
    @RequestMapping(value = "member/metersTagDataValue/findListOfDataId",method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    Object findListOfDataId(@RequestBody Map<String, Object> params);
}
