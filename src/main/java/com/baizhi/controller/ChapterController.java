package com.baizhi.controller;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import org.apache.ibatis.session.RowBounds;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterDao chapterDao;
    @Autowired
    AlbumDao albumDao;
    @RequestMapping("queryByPage")
    public Map querytByPage(Integer page,Integer rows,String row_id){
        HashMap map = new HashMap();
        Chapter chapter = new Chapter();
        chapter.setAlbumId(row_id);
        int i = chapterDao.selectCount(chapter);
        int total =i%rows==0?i/rows:i/rows+1;
        List<Chapter> chapters = chapterDao.selectByRowBounds(chapter, new RowBounds(page - 1, rows));
        map.put("records",i);
        map.put("total",total);
        map.put("page",page);
        map.put("rows",chapters);




        return  map;
    }
    @RequestMapping("edit")
    public Map edit(Chapter chapter,String oper,String row_id){
        HashMap map = new HashMap();
        if(oper.equals("add")){
            chapter.setAlbumId(row_id);
            String id = UUID.randomUUID().toString();
            chapter.setId(id);
            chapterDao.insert(chapter);
            Album album = new Album();
            album.setId(row_id);
            Album album1 = albumDao.selectByPrimaryKey(album);
            album1.setCount(album1.getCount()+1);
            albumDao.updateByPrimaryKeySelective(album1);
            map.put("chapterID",id);

        }
        if(oper.equals("edit")){
            chapter.setAlbumId(row_id);
            if(chapter.getUrl().equals("")){
                chapter.setUrl(null);
            }

            chapterDao.updateByPrimaryKeySelective(chapter);
            map.put("chapterID",chapter.getId());
        }
        if(oper.equals("del")){
            chapterDao.delete(chapter);
            Album album = new Album();
            album.setId(row_id);
            Album album1 = albumDao.selectByPrimaryKey(album);
            album1.setCount(album1.getCount()-1);
            albumDao.updateByPrimaryKeySelective(album1);
        }
        return map;
    }

/*

    @RequestMapping("upload")
    public Map upload(MultipartFile cover, String albumId, HttpSession session ) throws IOException {
        HashMap hashMap = new HashMap();

        String realPath = session.getServletContext().getRealPath("/upload");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String name = new Date().getTime() + "-" + cover.getOriginalFilename();
        Album album = new Album();
        album.setId(albumId);
        album.setCover("/upload/" + name);
        albumDao.updateByPrimaryKeySelective(album);
        cover.transferTo(new File(realPath, name));

        hashMap.put("status", 200);


        return hashMap;
    }
*/



    @RequestMapping("upload")
    public Map upload(MultipartFile url,String chapterID,HttpSession session) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        HashMap map = new HashMap();
        if(!url.getOriginalFilename().equals("")){
            String realPath = session.getServletContext().getRealPath("/upload");
            File file = new File(realPath);

            String name =new Date().getTime()+"-"+url.getOriginalFilename();
            url.transferTo(new File(realPath,name));
            Chapter chapter = new Chapter();
            chapter.setId(chapterID);
            chapter.setUrl("/upload/"+name);
       /* System.out.println(url.getSize());
       Double size = Double.valueOf(url.getSize()/1024/1024);
        System.out.println("+++++++++++++++++++++++++++++++++"+Double.valueOf(url.getSize()/1024/1024));*/
            double size = (double) url.getSize();
            double dd= size/1024/1024;
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String sizes = decimalFormat.format(dd)+"MB";


            chapter.setSize(dd);



            AudioFile read = AudioFileIO.read(new File(realPath, name));
            MP3AudioHeader audioHeader = (MP3AudioHeader) read.getAudioHeader();
            int trackLength = audioHeader.getTrackLength();
            String time=trackLength/60+"分"+trackLength%60+"秒";
            chapter.setTime(time);
            chapterDao.updateByPrimaryKeySelective(chapter);
            map.put("status",200);

        }

        return map;
    }
}
