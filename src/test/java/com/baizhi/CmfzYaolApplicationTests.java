package com.baizhi;


import com.alibaba.druid.sql.ast.expr.SQLCaseExpr;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.*;
import com.baizhi.entity.*;


import com.baizhi.service.BannerService;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
class CmfzYaolApplicationTests {

    @Autowired
    AdminDao adminDao;

    @Test
    void contextLoads() {
        List<Admin> admins = adminDao.selectAll();
        System.out.println(admins);

    }

    @Test
    void testMapper() {
        Admin admin = new Admin(null, "admin", null);
        List<Admin> select = adminDao.select(admin);
        for (Admin admin1 : select) {
            System.out.println(admin1);
        }
    }

    @Test
    void testMapperBYkey() {
        /*Admin admin = adminDao.selectByPrimaryKey("1");
        System.out.println(admin);*/

      /*  int i = adminDao.selectCount(null);
        System.out.println(i);*/
        Admin admin = new Admin(null, "admin", null);
        List<Admin> select = adminDao.select(admin);
        System.out.println(select);
    }

    @Test
    void testpage() {
        List<Admin> admins = adminDao.selectByRowBounds(null, new RowBounds(1, 2));
        System.out.println(admins);
    }

    @Test
    void testExample() {
        Example example = new Example(Admin.class);
        example.and().andIn("id", Arrays.asList("1","3"));
        List<Admin> admins = adminDao.selectByExample(example);
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    @Test
    void testInsert() {
        int wjj = adminDao.insert(new Admin(UUID.randomUUID().toString(), "wjj", "123456"));
    }

    @Test
    void testUpdate() {
        Admin admin = new Admin("3", "小小", "1234564");
        adminDao.updateByPrimaryKey(admin);


    }

    @Test
    void testDel() {
       adminDao.deleteByIdList(Arrays.asList("2","4"));
    }

    @Test
    void testInsert1() {
        adminDao.insertList(Arrays.asList(new Admin("7", "sdf", "1234564"), new Admin("8", "sdd", "123456")));

        List<Admin> select = adminDao.select(null);
        for (Admin admin : select) {
            System.out.println(admin);
        }
    }


    @Test
    void testAdmin() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        Admin admin1 = adminDao.selectOne(admin);
        System.out.println(admin1);

    }
    @Autowired
    BannnerDao bannnerDao;
    @Autowired
    BannerService bannerService;
    @Test
    void testBanner() {
        Map map = bannerService.queryBYPage(1, 2);
        System.out.println(map);
    }

    @Test
    void testBannerUpda() {
        Banner banner = new Banner();
        banner.setId("0eeef2a9-3e2e-4f9d-b399-811c321ec9fa");
        banner.setDes("这是一垃圾");
        banner.setUrl(null);
        bannnerDao.updateByPrimaryKeySelective(banner);
    }
     @Autowired
    ChapterDao chapterDao;

    @Test
    void testChapter() {
        Chapter chapter = new Chapter();
        chapter.setAlbumId("1");
        int i = chapterDao.selectCount(null);
        System.out.println(i);
    }
    @Autowired
    AlbumDao albumDao;
    @Test
    void pricate() {
        Album album = new Album();
        album.setId("1");
        album.setAuthor("lss");

        albumDao.updateByPrimaryKeySelective(album);
    }

    @Autowired
    HttpSession session;

    @Test
    void testContext() {
        String realPath = session.getServletContext().getRealPath("/upload");
        System.out.println(realPath);
        List<Banner> banners = bannnerDao.selectAll();

        for (Banner banner : banners) {
            String url = banner.getUrl();
            banner.setUrl(realPath+url);
            System.out.println(banner.getUrl());
        }
    }

    @Autowired
    UserDao userDao;

    @Test
    void testUser() {
        Integer a = userDao.queryByTime("0", 1);
        System.out.println(a);
    }

    @Test
    void testmap() {
        List<Pro> pros = userDao.queryByPro("0");
        for (Pro pro : pros) {
            System.out.println(pro);
        }

    }

    @Test
    void testusetlogin() {
        User user = new User();
        user.setPhone("13561152599");
        List<User> select = userDao.select(user);

        System.out.println(select);
    }

    @Test
    void testtsms() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FmTHawk8dHdNqbpQHZF", "WpeKbmKXxsJzDkubUZ5CpNXmefWs3K ");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "3561152599");
        request.putQueryParameter("SignName", "品优购");
        request.putQueryParameter("TemplateCode", "SMS_181868447");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    @Autowired
    ItermDao itermDao;
    @Test
    void testIterm() {
        Iterm iterm = new Iterm();
        iterm.setUid("11980749-f8ee-4f31-b57b-5e8a48f589f4");
        List<Iterm> select = itermDao.select(iterm);
        for (Iterm iterm1 : select) {
            System.out.println(iterm1);
        }
    }


}