package com.baizhi.dao;

import com.baizhi.entity.Admin;
import com.baizhi.entity.Pro;
import com.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserDao extends InsertListMapper<User>, Mapper<User>, DeleteByIdListMapper<User,String> {
    public Integer queryByTime(@Param("sex") String sex,@Param("day") Integer day);
    public List<Pro> queryByPro(String sex);
}
