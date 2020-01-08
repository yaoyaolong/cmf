package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dao.BannnerDao;
import com.baizhi.entity.Banner;
import com.sun.deploy.net.URLEncoder;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@RequestMapping("poi")
@Controller
public class PoiController {

    @Autowired
    BannnerDao bannnerDao;
    @RequestMapping("poia")

    public void poia(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Banner> banners = bannnerDao.selectAll();
       String upload="E:\\a\\cmfz-yaol\\src\\main\\webapp";
        for (Banner banner : banners) {
            String url = banner.getUrl();
            banner.setUrl(upload+url);
        }
        System.out.println(banners);

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图","轮播图"),Banner.class, banners);


        workbook.write(new FileOutputStream(new File("E:/easypoi.xls")));
        workbook.close();

        FileInputStream inputStream = new FileInputStream(new File("E:/easypoi.xls"));


        response.setHeader("content-disposition","attachment;fileName="+ URLEncoder.encode("easypoi.xls","UTF-8"));


        ServletOutputStream outputStream = response.getOutputStream();


        IOUtils.copy(inputStream,outputStream);



    }

}
