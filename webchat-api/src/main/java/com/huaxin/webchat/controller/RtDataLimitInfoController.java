package com.huaxin.webchat.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.webchat.service.RtDataLimitInfoService;
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
 * 告警条件
 */
@RestController
@RequestMapping("/rtDataLimitInfo")
public class RtDataLimitInfoController {


    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private RtDataLimitInfoService rtDataLimitInfoService;

    /**
     * 查询告警条件
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = rtDataLimitInfoService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 根据Id查询微信告警条件
     * @param params
     * @return
     */
    @RequestMapping(value = "/findOfById", method = RequestMethod.POST)
    public Result findOfById(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = rtDataLimitInfoService.findOfById(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/saveOfUpdateConfig", method = RequestMethod.POST)
    public Result saveOfUpdateConfig(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            rtDataLimitInfoService.saveOfUpdateInfo(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteRtDataLimitInfo", method = RequestMethod.POST)
    public Result deleteRtDataLimitInfo(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            rtDataLimitInfoService.deleteRtDataLimitInfo(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
