package com.huaxin.member.controller;





import com.huaxin.member.service.BaseUserService;
import com.huaxin.member.unit.base.Result;
import com.huaxin.member.unit.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/baseUser")
public class BaseUserController {


    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private BaseUserService baseUserService;

    /**
     * 查询用户信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findByUser", method = RequestMethod.POST)
    public Result findByUser(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = baseUserService.findByUser(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }


    /**
     * 查询部门信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findHoDepartment", method = RequestMethod.POST)
    public Result findHoDepartment(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = baseUserService.findHoDepartment(params);
            result.setData(list);
        }catch (Exception e){
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }



}
