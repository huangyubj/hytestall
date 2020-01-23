package com.hy.service.imp;

import com.hy.demo.mybatis.entity.CscLine;
import com.hy.demo.mybatis.mapper.CscLineMapper;
import com.hy.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//简单操作
@Service("lineService")
public class LineServiceImp implements LineService {
    protected static final String CACHENAME  = "csc_line_";
    @Autowired
    private CscLineMapper cscLineMapper;

    @Override
    public CscLine queryLine(long lineId) {
        CscLine cscLine = cscLineMapper.selectByPrimaryKey(lineId);
        return cscLine;
    }


    @Override
    public int update(CscLine record){
        int i = cscLineMapper.updateByPrimaryKeySelective(record);
        return i;
    }

    @Override
    public int delete(long lineId) {
        int i = cscLineMapper.deleteByPrimaryKey(lineId);
        return i;
    }

    @Override
    public List<CscLine> list() {
        return cscLineMapper.list();
    }
}
