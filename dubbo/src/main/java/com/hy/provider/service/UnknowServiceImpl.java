package com.hy.provider.service;

import com.hy.api.entity.InfoEntity;
import com.hy.api.service.InfoService;
import com.hy.api.service.UnknowService;
import org.springframework.stereotype.Component;

@Component("unknowServiceImpl")
public class UnknowServiceImpl implements UnknowService {
    public UnknowServiceImpl() {
        super();
    }

    public InfoEntity getDetail(String id) {
        InfoEntity info = new InfoEntity(id, "little fat", "a little fat");
        return info;
    }
}
