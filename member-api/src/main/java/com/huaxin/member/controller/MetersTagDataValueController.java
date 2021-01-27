package com.huaxin.member.controller;

import com.huaxin.member.service.MetersTagDataValueService;
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
@RequestMapping("/metersTagDataValue")
public class MetersTagDataValueController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MetersTagDataValueService metersTagDataValueService;


    /**
     * 查询数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/findListOfDataId", method = RequestMethod.POST)
    public Result findListOfDataId(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = metersTagDataValueService.findListOfDataId(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


}
