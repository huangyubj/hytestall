package com.hy.service.impl;

import com.hy.constan.CommonStatusConstant;
import com.hy.entity.Messageflow;
import com.hy.mapper.MessageflowMapper;
import com.hy.service.YuebaoService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
        messageflowMapper.insert(messageflow);
        sendMessage(messageflow);
    }

    public void sendMessage(Messageflow messageflow) {

    }

    public String updateMessageflow(Messageflow messageflow) {

        return CommonStatusConstant.STATUS_SUCCESS;
    }
}
