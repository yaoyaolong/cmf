package com.baizhi.service.serviceImpl;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminDao adminDao;

    @Override
    public HashMap<String, Object> login(String enCode, String username, String password, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        Admin admin = new Admin();
        admin.setUsername(username);
        Admin admin1 = adminDao.selectOne(admin);
        String imageCode = (String) request.getSession().getAttribute("imageCode");

        if(imageCode.equals(enCode)){

            if(username.equals(admin1.getUsername())){

                if(password.equals(admin1.getPassword())){


                    request.getSession().setAttribute("admin",admin);

                    map.put("success","200");
                    map.put("message","登陆成功");
                }else{
                    map.put("success","400");
                    map.put("message","密码错误");
                }
            }else{
                map.put("success","400");
                map.put("message","用户不存在");
            }
        }else{
            map.put("success","400");
            map.put("message","验证码错误");
        }


        return map;
    }
}
