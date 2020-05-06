package com.hy.service.impl;

import com.alibaba.fastjson.JSON;
import com.hy.constan.CommonStatusConstant;
import com.hy.dao.MessageflowMapper;
import com.hy.entity.Messageflow;
import com.hy.service.YuebaoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class YuebaoServiceImpl implements YuebaoService {

    @Autowired
    private MessageflowMapper messageflowMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void doPay(Messageflow messageflow) {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDHHmmss_");
        String messageid = UUID.randomUUID().toString();
        messageflow.setId(messageid);
        messageflow.setStatus(CommonStatusConstant.MESSAGE_STATUS_DRAFT);
        messageflow.setCreatedate(new Date());
        messageflow.setUpdatedate(new Date());
        messageflowMapper.insert(messageflow);
        sendMessage(messageflow);
    }

    /**
     * 发送消息到 send 队列
     * @param messageflow
     */
    public void sendMessage(Messageflow messageflow) {
        String msg1 = JSON.toJSONString(messageflow);
        rabbitTemplate.convertAndSend(CommonStatusConstant.MESSAGE_EXCHANGE, CommonStatusConstant.MESSAGE_QUEUE_SEND, msg1);
    }

    public String updateMessageflow(Messageflow messageflow) {

        return CommonStatusConstant.STATUS_SUCCESS;
    }
}
