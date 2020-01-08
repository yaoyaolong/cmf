package com.baizhi.controller;

import com.baizhi.dao.LoggsDao;
import com.baizhi.entity.Loggs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("loggs")
public class LoggsController {
    @Autowired
    LoggsDao loggsDao;
    @RequestMapping("select")
    public List<Loggs> select(){
        List<Loggs> loggs = loggsDao.selectAll();
        return loggs;
    }
}
