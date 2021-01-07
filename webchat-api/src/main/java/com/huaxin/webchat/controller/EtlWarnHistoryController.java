package com.huaxin.webchat.controller;

import com.github.pagehelper.PageInfo;
import com.huaxin.webchat.service.EtlWarnHistoryService;
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
 * 微信告警记录
 */
@RestController
@RequestMapping("/etlWarnHistory")
public class EtlWarnHistoryController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private EtlWarnHistoryService etlWarnHistoryService;


    /**
     * 查询微信告警记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            PageInfo list = etlWarnHistoryService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 根据Id查询微信告警记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/findOfById", method = RequestMethod.POST)
    public Result findOfById(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = etlWarnHistoryService.findOfById(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
