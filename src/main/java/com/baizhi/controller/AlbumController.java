package com.baizhi.controller;

import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;

import org.apache.ibatis.session.RowBounds;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("album")
public class AlbumController {
  @Autowired
    AlbumDao albumDao;
  @RequestMapping("queryByPage")
  @ResponseBody
  public Map queryByPage(Integer page,Integer rows){
      HashMap map = new HashMap();
      int records = albumDao.selectCount(null);
      int total =records%rows==0?records/rows:records/rows+1;
      List<Album> albums = albumDao.selectByRowBounds(null, new RowBounds(page - 1, rows));
      map.put("records",records);
      map.put("total",total);
      map.put("page",page);
      map.put("rows",albums);


      return  map;
  }
  @RequestMapping("edit")
  @ResponseBody
  public Map edit(String oper,Album album){
      HashMap hashMap = new HashMap();
      if(oper.equals("add")){
          String albumId = UUID.randomUUID().toString();
          album.setId(albumId);
          albumDao.insert(album);
          hashMap.put("albumId",albumId);
      }
      if(oper.equals("edit")){
          if(album.getCover().equals("")){
              album.setCover(null);
          }
          System.out.println("+++++++++++++++++++++++++++++++++"+album);

         albumDao.updateByPrimaryKeySelective(album);
          hashMap.put("albumId",album.getId());
      }
      if(oper.equals("del")){
       albumDao.delete(album);
      }

      return  hashMap;
  }
   /* @RequestMapping("upload")
    public Map upload(MultipartFile cover, String bannerId, HttpSession session ) throws IOException {

        HashMap map = new HashMap();


        if( !cover.getOriginalFilename().equals("")) {
            String realPath = session.getServletContext().getRealPath("/upload");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String name = new Date().getTime() + "-" + cover.getOriginalFilename();
            Banner banner = new Banner();
            banner.setId(bannerId);
            banner.setUrl("/upload/" + name);
            bannerService.updateOne(banner);
            url.transferTo(new File(realPath, name));
            map.put("status", 200);
        }

        return map;


    }*/

 @RequestMapping("upload")
 @ResponseBody
 public Map upload(MultipartFile cover,String albumId,HttpSession session ) throws IOException {
     HashMap hashMap = new HashMap();
        if(!cover.getOriginalFilename().equals("")){
            System.out.println("++++++++++++++++++++++++++++++++++++=");
            String realPath = session.getServletContext().getRealPath("/upload");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String name = new Date().getTime() + "-" + cover.getOriginalFilename();
            Album album = new Album();
            album.setId(albumId);
            album.setCover("/upload/" + name);
            cover.transferTo(new File(realPath, name));
            albumDao.updateByPrimaryKeySelective(album);
        }





         hashMap.put("status", 200);


     return hashMap;
 }
 @RequestMapping("play")
    public void play(String name, HttpServletRequest request, HttpServletResponse response) {
     System.out.println("++++++++++++++++++++++++++++++++++++++++++++++"+name);
     try {

         //1.获取文件的路径
         String realPath = request.getSession().getServletContext().getRealPath("");
         System.out.println(realPath);

         //2.根据路径获取文件
         FileInputStream inputStream = new FileInputStream(new File(realPath,name));

         //3.设置响应格式   响应头,文件名   attachment:以附件的形式下载   inline:在线打开
         response.setHeader("content-disposition","inline;fileName="+ URLEncoder.encode(name,"UTF-8"));

         //获取输出流
         ServletOutputStream outputStream = response.getOutputStream();

         //4.文件下载
         IOUtils.copy(inputStream,outputStream);

         //关闭资源
        inputStream.close();
         outputStream.close();

     } catch (Exception e) {
         e.printStackTrace();
     }


 }
}

