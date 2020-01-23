package com.hy.service;

import com.hy.demo.mybatis.entity.CscLine;
import com.hy.demo.mybatis.mapper.CscLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LineService {

    public CscLine queryLine(long lineId);
    public int update(CscLine record);
    public int delete(long lineId);
    public List<CscLine> list();
}
