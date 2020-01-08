package com.baizhi.controller;

import com.baizhi.dao.GuruDao;
import com.baizhi.entity.Article;
import com.baizhi.entity.Guru;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("guru")
public class GuruController {
    @Autowired
    GuruDao guruDao;
    @RequestMapping("showALLGuru")
    public List<Guru> showALLGuru(){
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }
    @RequestMapping("show")
    public Map show(Integer page, Integer rows){
        HashMap map = new HashMap();
        int records = guruDao.selectCount(null);
        int total =records%rows==0?records/rows:records/rows+1;
        List<Guru> gurus = guruDao.selectByRowBounds(null, new RowBounds(page - 1, rows));
        map.put("records",records);
        map.put("total",total);
        map.put("page",page);
        map.put("rows",gurus);
        return map;




    }


    @RequestMapping("updateStatus")
    public Map updateStatus(String id,String value){
        HashMap hashMap = new HashMap();
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++"+id);
        System.out.println(value);
        if(value.equals("0")){
            Guru guru = new Guru();
            guru.setId(id);
            guru.setStatus("1");
            guruDao.updateByPrimaryKeySelective(guru);
        }else {
            Guru guru = new Guru();
            guru.setId(id);
            guru.setStatus("0");
            guruDao.updateByPrimaryKeySelective(guru);
        }
        hashMap.put("status",200);
        return  hashMap;

    }
}
