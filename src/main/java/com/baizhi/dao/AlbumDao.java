package com.baizhi.dao;

import com.baizhi.entity.Album;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface AlbumDao extends InsertListMapper<Album>, Mapper<Album>, DeleteByIdListMapper<Album,String> {
    void selectByExampleAndRowBounds();
}
