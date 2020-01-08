package com.baizhi.dao;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Iterm;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ItermDao extends InsertListMapper<Iterm>, Mapper<Iterm>, DeleteByIdListMapper<Iterm,String> {
}
