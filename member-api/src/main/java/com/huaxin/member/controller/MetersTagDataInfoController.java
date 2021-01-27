package com.huaxin.member.controller;

import com.huaxin.member.service.MetersTagDataInfoService;
import com.huaxin.member.unit.base.Result;
import com.huaxin.member.unit.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/metersTagDataInfo")
public class MetersTagDataInfoController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MetersTagDataInfoService metersTagDataInfoService;

    /**
     * 查询点位信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findList", method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = metersTagDataInfoService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
