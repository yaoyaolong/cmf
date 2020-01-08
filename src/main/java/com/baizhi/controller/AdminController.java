package com.baizhi.controller;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("admin")
public class AdminController {
    @RequestMapping("getImageCode")
    public  void getImageCode(HttpServletRequest request, HttpServletResponse response){


        String code = ImageCodeUtil.getSecurityCode();


        request.getSession().setAttribute("imageCode",code);


        BufferedImage image = ImageCodeUtil.createImage(code);


        response.setContentType("image/png");


        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Autowired
    AdminService adminService;
    @RequestMapping("login")
    @ResponseBody
    public HashMap<String, Object> login(String enCode, String username, String password, HttpServletRequest request){
        HashMap<String, Object> map = adminService.login(enCode, username, password, request);

        return map;
    }
    @Autowired
    HttpSession session;

    @Autowired
    AdminDao adminDao;
     @RequestMapping("login1")
     @ResponseBody
    public Map Adminlogin(Admin admin) {
         HashMap<String, Object> map = new HashMap<>();
         Admin admin1 = new Admin();
         admin1.setUsername(admin.getUsername());
         Admin admin2 = adminDao.selectOne(admin1);
         if(admin2==null){
             map.put("status","400");
             map.put("mgs","用户名不存在");

         }else {
             if(!admin2.getPassword().equals(admin.getPassword())){
                 map.put("status","400");
                 map.put("mgs","密码错误");

             }else {
                 map.put("status","200");
                 session.setAttribute("admin",admin);

             }
         }
        return map;
     }
}
