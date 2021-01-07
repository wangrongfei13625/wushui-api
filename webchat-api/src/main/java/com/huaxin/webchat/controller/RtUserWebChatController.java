package com.huaxin.webchat.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.webchat.service.RtUserWebChatService;
import com.huaxin.webchat.unit.base.Result;
import com.huaxin.webchat.unit.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 用户微信
 */
@RestController
@RequestMapping("/rtUserWebChat")
public class RtUserWebChatController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private RtUserWebChatService rtUserWebChatService;

    /**
     * 查询所有用户微信
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = rtUserWebChatService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 根据条件查询用户微信
     * @param params
     * @return
     */
    @RequestMapping(value = "/findOfById", method = RequestMethod.POST)
    public Result findOfById(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = rtUserWebChatService.findOfById(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "/saveOfUpdateWebChat", method = RequestMethod.POST)
    public Result saveOfUpdateWebChat(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            rtUserWebChatService.saveOfUpdateWebChat(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteWebChat", method = RequestMethod.POST)
    public Result deleteWebChat(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            rtUserWebChatService.deleteWebChat(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
