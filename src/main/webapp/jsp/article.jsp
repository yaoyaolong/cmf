<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#articleTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/article/selectAll",
                datatype : "json",
                colNames : [ 'id', '标题', '图片', '内容', '创建时间','发布时间', '状态','所属上师', '操作',  ],
                colModel : [
                    {name : 'id',align:"center",hidden: true},
                    {name : 'title',align:"center",editable: true,editrules:{required:true}},
                    {name : 'img',align:"center",edittype:"file",editoptions:{enctype:"multipart/form-data"},editable: true,
                        formatter:function(data){
                            return "<img src='${pageContext.request.contextPath}"+data+"' style='width:100px;height:80px' >";
                        }
                    },
                    {name : 'content',align:"center",editable:true},
                    {name : 'createDate',align:"center",editable:true,editrules:{required:true},edittype:"date"

                    },
                    {name : 'publishDate',align:"center",editable:true,editrules:{required:true},edittype:"date"

                    },
                    {name : 'status',align:"center" ,editable:true,editrules:{required:true},
                        formatter:function (data) {
                            if(data=="1"){
                                return "展示";
                            }else{
                                return "冻结";
                            }
                        }
                    },
                    {
                        name: 'guruId',
                        align: "center",
                        formatter:function (data) {
                            if(data=="1"){
                                return "zss";
                            }else{
                                return "lbb";
                            }
                        }
                    },
                    {
                        name:'option',
                        formatter:function (cellvalue, options, rowObject) {
                            var button = "<button type=\"button\" class=\"btn btn-primary\" onclick=\"update('"+rowObject.id+"')\">修改</button>&nbsp;&nbsp;";
                            button+= "<button type=\"button\" class=\"btn btn-danger\" onclick=\"del('"+rowObject.id+"')\">删除</button>";
                            return button;
                        }
                    }
                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#bannerPager',
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",

                styleUI:"Bootstrap",
                autowidth:true,
               /* multiselect:true,*/
                height: "400px",

                editurl:"${pageContext.request.contextPath}/banner/editer"

            });
    });



    function add() {
        $("#kindForm")[0].reset();
        KindEditor.html("#editor_id","");

        $.ajax({
            url:"${pageContext.request.contextPath}/guru/showALLGuru",
            type:"post",
            datatype:"json",
            success:function (data) {
                var option="<option value=\"0\">请选择所属上师</option>";
                data.forEach(function (guru) {
                    option +="<option value="+guru.id+">"+guru.name+"</option>"
                })
                $("#guruId").html(option)

            }
        }),
        $("#myModal").modal("show")

    }
    function sub() {


        $.ajaxFileUpload({

            url:"${pageContext.request.contextPath}/article/insert",
            type: "post",
            data: {"id":$('#id').val(),"title":$('#name').val(),"content":$("#editor_id").val(),"status":$('#status').val(),"guruId":$('#guruId').val()},
            datatype: "json",
            fileElementId:"inputfile",
            success:function (data) {
                $("#articleTable").trigger("reloadGrid");
                $("#myModal").modal("hide");
            }

        })
    }
    function update(id) {
        // 使用jqGrid("getRowData",id) 目的是屏蔽使用序列化的问题
        // $("#articleTable").jqGrid("getRowData",id); 该方法表示通过Id获取当前行数据
        var data = $("#articleTable").jqGrid("getRowData",id);

        $("#id").val(data.id);
        $("#name").val(data.title);
        // 更替KindEditor 中的数据使用KindEditor.html("#editor_id",data.content) 做数据替换
        KindEditor.html("#editor_id",data.content)
        // 处理状态信息
        $("#status").val(data.status);
        var option1 = "";
        if(data.status=="展示"){
            option1 += "<option selected value=\"1\">展示</option>";
            option1 += "<option value=\"2\">冻结</option>";
        }else{
            option1 += "<option value=\"1\">展示</option>";
            option1 += "<option selected value=\"2\">冻结</option>";
        }
        $("#status").html(option1);
        // 处理上师信息

        $.ajax({

            url: "${pageContext.request.contextPath}/guru/showALLGuru",
            datatype: "json",
            type: "post",
            success: function (data1) {
                // 遍历方法 --> forEach(function(集合中的每一个对象){处理})
                // 一定将局部遍历声明在外部
                var option2 = "<option value=\"0\">请选择所属上师</option>";
                data1.forEach(function (guru) {
                    if (guru.name==data.guruId){


                        option2 += "<option selected value=" + guru.id + ">" + guru.name + "</option>"
                    }
                    option2 += "<option value=" + guru.id + ">" + guru.name + "</option>"
                })
                $("#guruId").html(option2);
            }
        });
        $("#myModal").modal("show");
    }
    function del(id) {
        var data = $("#articleTable").jqGrid("getRowData",id);
        alert(data.id)

        $.ajax({
            url: "${pageContext.request.contextPath}/article/del?sid="+data.id,
            datatype: "get",
            type: "post",
            success:function () {
                $("#articleTable").trigger("reloadGrid");
            }
        })
    }


</script>
<div class="page-header">
    <h4>文章管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a >文章信息</a></li>
    <li><a onclick="add()">添加文章</a></li>



</ul>

<div id="loggs">

<table id="articleTable"></table>
<div id="articlePager" style="height: 50px"></div>

</div>