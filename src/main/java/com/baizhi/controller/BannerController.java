package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;



import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;
    @RequestMapping("selectAll")
    public List<Banner> selectAll() {




        List<Banner> banners = bannerService.selectAll();
        return banners;
    }

    @RequestMapping("queryByPage")
    public Map queryByPage(Integer page, Integer rows) {
        Map map = bannerService.queryBYPage(page, rows);
        return  map;
    }
    @RequestMapping("editer")
    public Map editer(Banner banner ,String oper,String[] id)  {
        HashMap<String, Object> map = new HashMap<>();
        if(oper.equals("add")){
         bannerService.addOne(banner);
         map.put("bannerId",banner.getId());
        }
        if(oper.equals("edit")){
            if(banner.getUrl().equals("")) {
                banner.setUrl(null);
            }


            bannerService.updateOne(banner);


            map.put("bannerId",banner.getId());
        }
        if(oper.equals("del")){
            bannerService.del(id);
        }



        return map;
    }
    @RequestMapping("upload")
    public Map upload(MultipartFile url, String bannerId, HttpSession session ) throws IOException {

            HashMap map = new HashMap();

/*
        System.out.println(url.getOriginalFilename()+"+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
*/
            if( !url.getOriginalFilename().equals("")) {
                String realPath = session.getServletContext().getRealPath("/upload");
                File file = new File(realPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String name = new Date().getTime() + "-" + url.getOriginalFilename();
                Banner banner = new Banner();
                banner.setId(bannerId);
                banner.setUrl("/upload/" + name);
                bannerService.updateOne(banner);
                url.transferTo(new File(realPath, name));
                map.put("status", 200);
            }

            return map;


        }

    @RequestMapping("testUpload")
    public void  testUpload(MultipartFile url,String bannerId,HttpSession session) throws IOException {


        if( url.getOriginalFilename()!=null){
        String realPath = session.getServletContext().getRealPath("/upload");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String name =new Date().getTime()+"-"+ url.getOriginalFilename();
        Banner banner = new Banner();
        banner.setId(bannerId);
        banner.setUrl("/upload/"+name);
        bannerService.updateOne(banner);
        url.transferTo(new File(name,realPath));

    }
    }

}
