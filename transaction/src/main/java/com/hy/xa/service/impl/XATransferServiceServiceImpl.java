package com.hy.xa.service.impl;

import com.hy.xa.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class XATransferServiceServiceImpl implements TransferService {

    @Autowired()
    @Qualifier("cscJdbcTemplate")
    private JdbcTemplate cscJdbcTemplate;

    @Autowired()
    @Qualifier("eamJdbcTemplate")
    private JdbcTemplate eamJdbcTemplate;

    @Override
    @Transactional
    public String transfer(String state) {
        cscJdbcTemplate.update("update csc_task_item set eq_status=? WHERE eq_id=1663632", state);
        eamJdbcTemplate.update("update eam_equt set equt_state=? WHERE equtid=1663632", state);
        if("e".equals(state)){
            throw new RuntimeException();
        }
        return "success";
    }
}
