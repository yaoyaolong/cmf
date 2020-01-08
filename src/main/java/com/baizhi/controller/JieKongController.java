package com.baizhi.controller;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ArticleDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.dao.ItermDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Article;

import com.baizhi.entity.Iterm;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("jk")
public class JieKongController {
    @Autowired
    ArticleDao articleDao;
    @Autowired
    AlbumDao albumDao;
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    ItermDao itermDao;
    @RequestMapping("article")
    public Map article(String uid,String id){
        HashMap map = new HashMap();
        Iterm iterm = new Iterm();
        iterm.setUid(uid);

        List<Iterm> iterms = itermDao.select(iterm);
        ArrayList list = new ArrayList();
        for (Iterm iterm1 : iterms) {
            Article article = new Article();
            article.setGuruId(iterm1.getGid());
            List<Article> list1 = articleDao.select(article);

        }
       map.put("status",200);
        map.put("articles",list);
        return map;
    }
    @RequestMapping("album")
    public Map album(String uid,String type){
        HashMap map = new HashMap();

        return null;
    }
    @RequestMapping("goeasy")
    public Map goeasy(){

        return null;
    }
}
