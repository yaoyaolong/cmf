package com.baizhi.dao;


import com.baizhi.entity.Loggs;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface LoggsDao extends InsertListMapper<Loggs>, Mapper<Loggs>, DeleteByIdListMapper<Loggs,String> {
}
