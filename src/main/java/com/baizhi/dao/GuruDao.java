package com.baizhi.dao;


import com.baizhi.entity.Guru;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface GuruDao extends InsertListMapper<Guru>, Mapper<Guru>, DeleteByIdListMapper<Guru,String> {
}
