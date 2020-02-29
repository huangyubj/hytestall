package com.hy.provider.service;

import com.hy.api.entity.InfoEntity;
import com.hy.api.service.InfoService;
import org.springframework.stereotype.Component;

@Component("infoServiceImpl")
public class InfoServiceImpl implements InfoService {
    public InfoServiceImpl() {
        super();
    }

    public InfoEntity getDetail(String id) {
        InfoEntity info = new InfoEntity(id, "little fat", "a little fat");
        return info;
    }
}
