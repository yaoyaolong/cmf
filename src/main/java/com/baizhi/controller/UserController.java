package com.baizhi.controller;

import cn.afterturn.easypoi.util.PoiPublicUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Guru;
import com.baizhi.entity.Pro;
import com.baizhi.entity.User;
import com.github.mustachejava.Code;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserDao userDao;
    @RequestMapping("ShwoAll")
    public Map ShwoAll(){
        HashMap map = new HashMap();
        ArrayList listman = new ArrayList<>();
        listman.add(userDao.queryByTime("0", 1));
        listman.add(userDao.queryByTime("0", 7));
        listman.add(userDao.queryByTime("0", 30));
        listman.add(userDao.queryByTime("0", 365));
        ArrayList listwm = new ArrayList();
        listwm.add(userDao.queryByTime("1", 1));
        listwm.add(userDao.queryByTime("1", 7));
        listwm.add(userDao.queryByTime("1", 30));
        listwm.add(userDao.queryByTime("1", 365));

        map.put("man",listman);
        map.put("woman",listwm);

       /* Integer a = userDao.queryByTime("0", 1);
        Integer a = userDao.queryByTime("0", 7);
        Integer a = userDao.queryByTime("0", 30);
        Integer a = userDao.queryByTime("0", 365);*/

        return map;
    }

    @RequestMapping("map")
    public Map map(){
        HashMap map = new HashMap();
        List<Pro> man = userDao.queryByPro("1");
        map.put("man",man);
        List<Pro> women = userDao.queryByPro("0");
       map.put("women",women);


        System.out.println(map);
        return map;
    }
    @RequestMapping("queryByPage")
    public Map queryByPage(Integer page,Integer rows){
        HashMap map = new HashMap();
        int records = userDao.selectCount(null);
        int total =records%rows==0?records/rows:records/rows+1;
        List<User> gurus = userDao.selectByRowBounds(null, new RowBounds(page - 1, rows));
        map.put("records",records);
        map.put("total",total);
        map.put("page",page);
        map.put("rows",gurus);
        return map;


    }


    @RequestMapping("edit")
    public Map edit(String oper,User user){
        HashMap hashMap = new HashMap();
        if(oper.equals("add")){
            String userId = UUID.randomUUID().toString();
            user.setId(userId);
            userDao.insert(user);
            hashMap.put("userId",userId);
        }
        if(oper.equals("edit")){
            if(user.getPhoto().equals("")){
                user.setPhoto(null);
            }
            System.out.println("+++++++++++++++++++++++++++++++++"+user);

            userDao.updateByPrimaryKeySelective(user);
            hashMap.put("userId",user.getId());
        }
        if(oper.equals("del")){
            userDao.delete(user);
        }

        return  hashMap;
    }


    @RequestMapping("upload")
    public Map upload(MultipartFile photo, String userId, HttpSession session ) throws IOException {
        HashMap hashMap = new HashMap();
        if(!photo.getOriginalFilename().equals("")){
            System.out.println("++++++++++++++++++++++++++++++++++++=");
            String realPath = session.getServletContext().getRealPath("/upload");
            File file = new File(realPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String name = new Date().getTime() + "-" + photo.getOriginalFilename();
            User user = new User();
            user.setId(userId);
            user.setPhoto("/upload/" + name);


            photo.transferTo(new File(realPath, name));
            userDao.updateByPrimaryKeySelective(user);
        }





        hashMap.put("status", 200);


        return hashMap;
    }

//前台接口文档
    @RequestMapping("login")
    public Map login(String phone, String password){
        HashMap map = new HashMap();
        User user = new User();
        user.setPhone(phone);
        User user1 = userDao.selectOne(user);
        System.out.println(user1);
       if(user1!=null){

           if(user1.getPassword().equals(password)){
               map.put("status","200");
               map.put("mes","正确");
               map.put("user",user1);


           }else{
               map.put("status","400");
               map.put("mes","用户密码不正确，请核实");
           }
       }else{
           map.put("status","400");
           map.put("mes","用户不存在");
       }


        return map;
    }
    @RequestMapping("regist")
    public  Map regist(String id,String password,String photo,String name,String nick_name,String sex,String sign,String location){
        HashMap hashMap = new HashMap();
        User user = new User();
        user.setPassword(password);
        user.setPhoto(photo);
        user.setName(name);
        user.setNickName(nick_name);
        user.setSex(sex);
        user.setSign(sign);
        user.setLocation(location);
        user.setId(id);
        /*Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andBetween("age",1,30);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andLike("name","%李%");
        example.or(criteria1);



        userDao.selectByExample(example);*/
        userDao.updateByPrimaryKeySelective(user);

        hashMap.put("status",user);
        return hashMap;

    }
    @RequestMapping("yzm")
    public Map yzm(String phone){
        HashMap map = new HashMap();
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FmTHawk8dHdNqbpQHZF", "WpeKbmKXxsJzDkubUZ5CpNXmefWs3K ");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "phone");
        request.putQueryParameter("SignName", "品优购");
        request.putQueryParameter("TemplateCode", "SMS_181868447");
        String s = UUID.randomUUID().toString();
        String code = s.substring(0, 3);
        request.putQueryParameter("TemplateParam", "{'code':"+code+"}");

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            map.put("status",200);
        } catch (ServerException e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("msg",e.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
            map.put("status",-200);
            map.put("msg",e.getMessage());
        }

        return map;
    }




}
