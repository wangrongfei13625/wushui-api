package com.huaxin.member.controller;




import com.huaxin.member.service.BaseUserService;
import com.huaxin.member.unit.base.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/baseUser")
public class BaseUserController {


    @Autowired
    private BaseUserService baseUserService;

    @RequestMapping(value = "/findByUser", method = RequestMethod.POST)
    public Result findByUser(@RequestBody Map<String, Object> params){

        Result result = new Result();
        try{
            List<Map<String,Object>> list = baseUserService.findByUser();
            result.setData(list);
        }catch (Exception e){

        }
        return result;
    }


}
