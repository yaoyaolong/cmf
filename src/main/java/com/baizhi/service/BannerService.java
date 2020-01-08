package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BannerService {
 public List<Banner> selectAll();
 public Map queryBYPage(Integer page,Integer rows);
 public void addOne(Banner banner);
 public void updateOne(Banner banner);
 public void del(String[] id);
}
