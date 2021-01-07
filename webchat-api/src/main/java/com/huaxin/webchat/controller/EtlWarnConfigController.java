package com.huaxin.webchat.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.webchat.service.EtlWarnConfigService;
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
 * 微信推送人员配置
 */
@RestController
@RequestMapping("/etlWarnConfig")
public class EtlWarnConfigController {

    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private EtlWarnConfigService etlWarnConfigService;

    /**
     * 查询微信推送人员配置
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = etlWarnConfigService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 根据Id查询微信推送人员配置
     * @param params
     * @return
     */
    @RequestMapping(value = "/findById", method = RequestMethod.POST)
    public Result findById(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
           List<Map<String,Object>> list = etlWarnConfigService.findById(params);
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
            etlWarnConfigService.saveOfUpdateConfig(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteEtlWarnConfig", method = RequestMethod.POST)
    public Result deleteEtlWarnConfig(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            etlWarnConfigService.deleteEtlWarnConfig(params);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
