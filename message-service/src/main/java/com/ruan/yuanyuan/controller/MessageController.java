package com.ruan.yuanyuan.controller;

import com.ruan.yuanyuan.entity.ResultObject;
import com.ruan.yuanyuan.service.IMessageService;
import com.ruan.yuanyuan.vo.RabbitMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: MessageController
 * @author: ruanyuanyuan
 * @date: 2020/7/18 16:47
 * @version: 1.0
 * @description: 消息控制器
 **/
@RestController
@RequestMapping("/api/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    @RequestMapping("/saveOrUpdate")
    public ResultObject saveOrUpdate(RabbitMessageVo rabbitMessageVo){
        messageService.saveAndUpdate(rabbitMessageVo);
        return new ResultObject();
    }
}
