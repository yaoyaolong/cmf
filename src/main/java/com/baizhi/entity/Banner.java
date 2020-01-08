package com.baizhi.entity;





import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.Id;


import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner implements Serializable {
  @Id
  @Excel(name = "id")
  private String id;
  @Excel(name = "标题")
  private String title;
  @Excel(name = "图片" ,type =2)
  private String url;
  @Excel(name = "超链接")
  private String href;
  @Excel(name = "日期",format = "yyyy年MM月dd日")
@JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date create_date;
  @Excel(name = "描述")
  private String des;
  @Excel(name = "状态")
  private String status;



}
