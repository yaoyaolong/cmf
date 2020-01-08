package com.baizhi.service.serviceImpl;

import com.baizhi.aspect.LogAnnotation;
import com.baizhi.dao.AdminDao;
import com.baizhi.dao.BannnerDao;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Banner;
import com.baizhi.service.AdminService;
import com.baizhi.service.BannerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class BannerServiceImpl implements BannerService {
   @Autowired
    BannnerDao bannnerDao;

    @Override
    public List<Banner> selectAll() {
        List<Banner> banners = bannnerDao.selectAll();
        return banners;
    }

    @Override
    public Map queryBYPage(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        //总条数
        int records = bannnerDao.selectCount(null);
        map.put("records",records);

        //总页数
        int total =records%rows==0?records/rows:records/rows+1;
        map.put("total",total);

        //当前页
        map.put("page",page);

        //每页展示数据
        List<Banner> bannerList = bannnerDao.selectByRowBounds(null, new RowBounds((page - 1) * rows, rows));
       map.put("rows",bannerList);

        return map;
    }
   @LogAnnotation(value = "添加")
    @Override
    public void addOne(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        bannnerDao.insert(banner);
    }
    @LogAnnotation(value = "修改")
    @Override
    public void updateOne(Banner banner) {
        bannnerDao.updateByPrimaryKeySelective(banner);
    }
    @LogAnnotation(value = "删除")
    @Override
    public void del(String[] id) {
        bannnerDao.deleteByIdList(Arrays.asList(id));
    }


}
