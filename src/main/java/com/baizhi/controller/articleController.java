package com.baizhi.controller;

import com.baizhi.dao.ArticleDao;

import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.RowBounds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("article")
public class articleController  {
    @Autowired
    ArticleDao articleDao;
    @RequestMapping("selectAll")
    public HashMap  selectAll(Integer page,Integer rows){
        HashMap map = new HashMap();
        int records = articleDao.selectCount(null);
        int total =records%rows==0?records/rows:records/rows+1;
        List<Article> albums = articleDao.selectByRowBounds(null, new RowBounds(page - 1, rows));
        map.put("records",records);
        map.put("total",total);
        map.put("page",page);
        map.put("rows",albums);
        return map;



    }
    @RequestMapping("uploadImg")
    public Map uploadImg(MultipartFile imgFile ,HttpServletRequest request) throws IOException {
        HashMap map = new HashMap();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String filename = imgFile.getOriginalFilename();
        String name = new Date().getTime()+"-"+filename;
        //文件上传
        imgFile.transferTo(new File(realPath,name));

        //获取   http
        String scheme = request.getScheme();

        //获取   localhost
        String serverName = request.getServerName();

        //获取 8989
        int serverPort = request.getServerPort();

        //获取项目名  /cmfz
        String contextPath = request.getContextPath();

        //拼接网络路径    "http://localhost:8081/cmfz/upload/"+name
        String url=scheme+"://"+serverName+":"+serverPort+contextPath+"/upload/editor/"+name;
        System.out.println(url);

        map.put("error",0);
        map.put("url",url);


        return  map;
    }
    @RequestMapping("showAllImg")
    public Map showALlImg(HttpServletRequest request){
        HashMap<String, Object> maps = new HashMap<>();

        ArrayList<Object> lists = new ArrayList<>();

        //获取文件的绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/editor");

        //获取文件
        File file = new File(realPath);

        //获取文件夹中所有的   文件名
        String[] names = file.list();

        //遍历文件名
        for (int i = 0; i < names.length; i++) {
            //文件名
            String name = names[i];

            /*
            *
                {
                  "is_dir": false,
                  "has_file": false,
                  "filesize": 14966,
                  "dir_path": "",
                  "is_photo": true,
                  "filetype": "jpg",
                  "filename": "1_192040_1.jpg",
                  "datetime": "2018-06-06 00:36:39"
                }
            * */
            HashMap<String, Object> map = new HashMap<>();

            map.put("is_dir",false);  //是否是文件夹
            map.put("has_file",false);  //是否有文件
            File file1 = new File(realPath, name);
            map.put("filesize",file1.length());  //文件的大小
            map.put("is_photo",true);  //是否是图片
            String extension = FilenameUtils.getExtension(name);
            map.put("filetype",extension);  //图片的类型
            map.put("filename",name);  //图片的名字

            //字符串拆分
            String[] strs = name.split("-");
            String times =strs[0];
            long time = Long.parseLong(times);
            //指定一个日期格式
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String datetime = dateFormat.format(time);

            /*"2018-06-06 00:36:39"*/
            map.put("datetime",datetime);  //图片上传时间

            //将数据放入集合
            lists.add(map);
        }

        /*
        *   "moveup_dir_path": "",
              "current_dir_path": "",
              "current_url": "/ke4/php/../attached/",
              "total_count": 5,
              "file_list":
        * */
        maps.put("current_url","http://localhost:8081/cmfz/upload/editor/");
        maps.put("total_count",lists.size());
        maps.put("file_list",lists);

        return maps;
    }
    @RequestMapping("insert")
    public String insert(Article article, MultipartFile inputfile,HttpSession session) throws IOException {
       if(article.getId().equals("")){
           System.out.println(article);
           System.out.println(inputfile);
           //文件上传
           String realPath = session.getServletContext().getRealPath("/upload");
           File file = new File(realPath);
           if (!file.exists()) {
               file.mkdirs();
           }
           String name =new Date().getTime()+"-"+ inputfile.getOriginalFilename();
           System.out.println(name);
           inputfile.transferTo(new File(realPath,name));
           article.setId(UUID.randomUUID().toString());
           article.setImg("/upload/"+name);
           articleDao.insert(article);

       }else{
/*

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

*/
            if(!inputfile.getOriginalFilename().equals("")){
                String realPath = session.getServletContext().getRealPath("/upload");
                String name = new Date().getTime() + "-" + inputfile.getOriginalFilename();
                article.setImg("/upload/"+name);
                inputfile.transferTo(new File(realPath, name));
            }
           articleDao.updateByPrimaryKeySelective(article);

       }
        return "200";
    }
   @RequestMapping("del")
    public  String del(String sid){
       System.out.println("++++++++++++++++++++++++++++id"+sid);
       Article article = new Article();
       article.setId(sid);
       articleDao.deleteByPrimaryKey(article);

        return "200";
   }



}
