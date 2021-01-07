package com.huaxin.webchat.controller;



import com.alibaba.fastjson.JSON;
import com.huaxin.webchat.service.BaseUserService;
import com.huaxin.webchat.unit.HttpUtils;
import com.huaxin.webchat.unit.PropUtil;
import com.huaxin.webchat.unit.base.Result;
import com.huaxin.webchat.unit.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/baseUser")
public class BaseUserController {

    private Logger logger = LogManager.getLogger(this.getClass());

    private final static String qy_user_url = "https://qyapi.weixin.qq.com/cgi-bin/agent/get?access_token=ACCESS_TOKEN&agentid=AGENTID";

    private static PropUtil propUtil = new PropUtil("/webChat.properties");



    @Autowired
    private BaseUserService baseUserService;

    @RequestMapping(value = "/findByUser", method = RequestMethod.POST)
    public Result findByUser(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = baseUserService.findByUser();
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 查询企业微信应用下所有的用户及微信号
     * @param params
     * @return
     */
    @RequestMapping(value = "/findWebChatOfUser", method = RequestMethod.POST)
    public Result findWebChatOfUser(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{

            String corpId = propUtil.getProperty("corpId");
            String corpSecret = propUtil.getProperty("corpSecret");
            String AgentId = propUtil.getProperty("AgentId");
            String access_token =getAccessToken(corpId,corpSecret);
            String requestURL = qy_user_url.replace("ACCESS_TOKEN", access_token).replace( "AGENTID", AgentId);

            try{

                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject( HttpUtils.doGet(requestURL));
                String allow_userinfos = jsonObject.getString("allow_userinfos");

                com.alibaba.fastjson.JSONObject obj = JSON.parseObject( allow_userinfos);
                //map对象
                Map<String, Object> data =new HashMap<>();

                Iterator it =obj.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it.next();
                    data.put(entry.getKey(), entry.getValue());
                }

                List<Map<String,Object>>  list = (List<Map<String,Object>>)data.get("user");

                result.setData(list);
            }catch (Exception e){
                e.printStackTrace();
            }


        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    private  String getAccessToken(String corpid, String corpsecret) {
        String url="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+corpid+"&corpsecret="+corpsecret+"";
        String token=null;
        try {
            token = HttpUtils.httpGetMethod(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }


}
