package com.baizhi.dao;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleDao extends InsertListMapper<Article>, Mapper<Article>, DeleteByIdListMapper<Article,String> {
}
