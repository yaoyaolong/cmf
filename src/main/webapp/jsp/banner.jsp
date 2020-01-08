<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#bannerTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/banner/queryByPage",
                datatype : "json",
                colNames : [ 'id', 'title', 'url', 'href', 'create_date','des', 'status' ],
                colModel : [
                    {name : 'id',align:"center",hidden: true},
                    {name : 'title',align:"center",editable: true,editrules:{required:true}},
                    {name : 'url',align:"center",edittype:"file",editoptions:{enctype:"multipart/form-data"},editable: true,
                        formatter:function(data){
                            return "<img src='${pageContext.request.contextPath}"+data+"' style='width:100px;height:80px' >";
                        }
                    },
                    {name : 'href',align:"center",editable:true},
                    {name : 'create_date',align:"center",editable:true,editrules:{required:true},edittype:"date"

                    },
                    {name : 'des',align:"center" ,editable:true,editrules:{required:true} },
                    {name : 'status',align : "center",editable:true,editrules:{required:true},edittype:"select",editoptions:{value:"1:展示;2:不展示"},
                        formatter:function(cellValue,option,row){
                            if(cellValue==1){
                                //展示
                                return "<button class='btn btn-warning' onclick='updateStatus(\""+row.id+"\",\""+cellValue+"\")' >展示</button>";
                            }else{
                                //不展示
                                return "<button class='btn btn-success' onclick='updateStatus(\""+row.id+"\",\""+cellValue+"\")' >不展示</button>";
                            }
                        }


                    },
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
                multiselect:true,
               height: "400px",

                editurl:"${pageContext.request.contextPath}/banner/editer"

            });
        jQuery("#bannerTable").jqGrid('navGrid', '#bannerPager', {edit : true,add : true,del : true,edittext:"编辑",addtext: "添加",deltext:"删除"},
            {
                closeAfterEdit:true,
                afterSubmit: function (response,postData) {
                    var bannerId=response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        type:"post",
                        datatype: "json",
                        data:{bannerId :bannerId},
                        fileElementId:"url",
                        success:function (data) {
                            $("#bannerTable").trigger("reloadGrid");
                        }




                    });
                    return postData;

                }
            },
            {
                closeAfterAdd: true,
                afterSubmit: function (response,postData) {
                    var bannerId=response.responseJSON.bannerId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/banner/upload",
                        type:"post",
                        datatype: "json",
                        data:{bannerId :bannerId},
                        fileElementId:"url",
                        success:function (data) {
                                $("#bannerTable").trigger("reloadGrid");
                        }




                    });
                    return postData;

                }



            },
            {
                closeAfterDel: true,
            }

        );
    })
function poia() {
    location.href="${pageContext.request.contextPath}/poi/poia"
}

</script>
<div class="page-header">
    <h4>轮播图管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a href="javascript: $('#centerLay').load('${pageContext.request.contextPath}/jsp/banner.jsp')" >轮播图</a></li>
    <li><a href="javascript: $('#loggs').load('${pageContext.request.contextPath}/jsp/logs.jsp')">轮播图日志管理</a></li>
    <li><button onclick="poia()" >轮播图导出</button></li>
    <li><button onclick="poib()">轮播图导入</button></li>



</ul>

<div id="loggs">

<table id="bannerTable"></table>
<div id="bannerPager" style="height: 50px"></div>

</div>