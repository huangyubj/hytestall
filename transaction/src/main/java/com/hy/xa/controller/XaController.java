package com.hy.xa.controller;

import com.hy.xa.service.TransferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "xa方式分布式事物",description = "xa方式分布式事物")
@RestController
@RequestMapping("/xa")
public class XaController {

    @Autowired
    private TransferService transferService;

    @ApiOperation("xa方式修改任务和设备状态")
    @RequestMapping(value = "/upEqState",method = RequestMethod.GET)
    public String update_eq_state(String state){
        transferService.transfer(state);
        return "success";
    }
}
