package com.baizhi.dao;


import com.baizhi.entity.Banner;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface BannnerDao extends InsertListMapper<Banner>, Mapper<Banner>, DeleteByIdListMapper<Banner,String> {
}
