<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#bannerTable1").jqGrid(
            {
                url : '${pageContext.request.contextPath}/album/queryByPage',
                datatype : "json",
                height : 500,
                colNames : [ 'id', 'title', 'score', 'author', 'broadcast','count', 'des','status','createDate','cover'],
                colModel : [
                    {name : 'id',hidden:true,align:"center"},
                    {name : 'title',editable:true,align:"center",editrules:{required:true}},
                    {name : 'score',editable:true,align:"center",editrules:{required:true}},
                    {name : 'author',editable:true,align:"center",editrules:{required:true}},
                    {name : 'broadcast',editable:true,align:"center",editrules:{required:true}},
                    {name : 'count',editable:true,align:"center",editrules:{required:true}},
                    {name : 'des',editable:true,align:"center",editrules:{required:true}},
                    {name : 'status',editable:true,align:"center",editrules:{required:true},edittype: "select",editoptions:{value:"1:展示;2:不展示"},formatter:function (data){
                         if(data=="1"){ return "展示"}else {
                             return "冻结"
                         }
                        }},
                    {name : 'createDate',editable:true,align:"center",editrules:{required:true},edittype:"date"},
                    {name : 'cover',editable:true,align:"center",edittype:"file",editoptions:{enctype:"multipart/form-data"},editable: true,
                        formatter:function (data) {
                            return "<img  style='height: 80px;width: 100px' src='${pageContext.request.contextPath}"+data+"' >"
                        }}
                ],
                rowNum : 8,
                rowList : [ 8, 10, 20, 30 ],
                pager : '#bannerPager1',
                sortname : 'id',
                viewrecords : true,
                sortorder : "desc",
                multiselect : false,
                subGrid : true,
                autowidth: true,
                editurl:'${pageContext.request.contextPath}/album/edit',
                styleUI: "Bootstrap",
                caption : "Grid as Subgrid",
                subGridRowExpanded : function(subgrid_id, row_id) {

                    var subgrid_table_id, pager_id;
                    subgrid_table_id = subgrid_id + "_t";
                    pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html(
                        "<table id='" + subgrid_table_id
                        + "' class='scroll'></table><div id='"
                        + pager_id + "' class='scroll'></div>");
                    jQuery("#" + subgrid_table_id).jqGrid(
                        {
                            url : "${pageContext.request.contextPath}/chapter/queryByPage?row_id="+row_id,
                            datatype : "json",
                            colNames : [ 'id', '标题', '大小', '时长','创建时间','music' ],
                            colModel : [
                                {name : "id",  align:"center",hidden: true,editable:true,align:"center"},
                                {name : "title",align:"center",editable:true},
                                {name : "size",align:"center",},
                                {name : "time",align:"center",},
                                {name : "createTime",align:"center",editable:true,edittype:"date"},
                                {name : "url",align:"center",editable:true,edittype:"file",editoptions:{enctype:"multipart/form-data"},
                                    formatter:function (cellvalue, options, rowObject) {
                                   var button="<button type=\"button\" class=\"btn btn-success\" onclick='play(\""+cellvalue+"\")'>下载</button>     ";
                                   button+="<button type=\"button\" class=\"btn btn-info\" onclick='play(\""+cellvalue+"\")' >播放</button>"


                                        return button;
                                    }}
                            ],
                            rowNum : 20,
                            pager : pager_id,
                            sortname : 'num',
                            sortorder : "asc",
                            height : '100%',
                            autowidth: true,
                            styleUI: "Bootstrap",
                            editurl: "${pageContext.request.contextPath}/chapter/edit?row_id="+row_id
                        });
                    jQuery("#" + subgrid_table_id).jqGrid('navGrid',
                        "#" + pager_id, {
                            edit : true,

                            add : true,
                            del : true
                        },
                        {
                            closeAfterEdit: true,
                            afterSubmit: function (response,postData) {
                                var chapterID=response.responseJSON.chapterID;
                                $.ajaxFileUpload({
                                    url:"${pageContext.request.contextPath}/chapter/upload",
                                    type:"post",
                                    datatype: "json",
                                    data:{chapterID :chapterID},
                                    fileElementId:"url",
                                    success:function (data) {
                                        $("#bannerTable1").trigger("reloadGrid");
                                    }




                                });
                                return postData;

                            }
                        },
                        {
                            closeAfterAdd: true,
                            afterSubmit: function (response,postData) {
                                var chapterID=response.responseJSON.chapterID;
                                $.ajaxFileUpload({
                                    url:"${pageContext.request.contextPath}/chapter/upload",
                                    type:"post",
                                    datatype: "json",
                                    data:{chapterID :chapterID},
                                    fileElementId:"url",
                                    success:function (data) {
                                        $("#bannerTable1").trigger("reloadGrid");
                                    }




                                });
                                return postData;

                            }
                        },
                        {
                            closeAfterDel: true,

                        });
                },
                subGridRowColapsed : function(subgrid_id, row_id) {
                    // this function is called before removing the data
                    //var subgrid_table_id;
                    //subgrid_table_id = subgrid_id+"_t";
                    //jQuery("#"+subgrid_table_id).remove();
                }
            });
        $("#bannerTable1").jqGrid('navGrid', '#bannerPager1', {
            add : true,
            edit : true,
            del : true
        },
            {
                closeAfterEdit:true,
                afterSubmit: function (response,postData) {
                    var albumId=response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        type:"post",
                        datatype: "json",
                        data:{albumId :albumId},
                        fileElementId:"cover",
                        success:function (data) {
                            $("#bannerTable1").trigger("reloadGrid");
                        }




                    });
                    return postData;

                }


            },
            {
                closeAfterAdd:true,
                afterSubmit: function (response,postData) {
                    var albumId=response.responseJSON.albumId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/album/upload",
                        type:"post",
                        datatype: "json",
                        data:{albumId :albumId},
                        fileElementId:"cover",
                        success:function (data) {
                            $("#bannerTable1").trigger("reloadGrid");
                        }




                    });
                    return postData;

                }
            },
            {
                closeAfterDel:true
            });
    })

    function play(cellvalue) {
        alert(cellvalue)
       /* location.href="${pageContext.request.contextPath}/album/play?name="+cellvalue;*/
        window.open("${pageContext.request.contextPath}/album/play?name="+cellvalue);
    }


</script>
<div class="page-header">
    <h4>专辑管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a href="javascript: $('#centerLay').load('${pageContext.request.contextPath}/jsp/album.jsp')" >专辑详情</a></li>
    <%--<li><a href="javascript: $('#loggs').load('${pageContext.request.contextPath}/jsp/logs.jsp')">轮播图日志管理</a></li>--%>



</ul>

<div id="loggs">

    <table id="bannerTable1"></table>
    <div id="bannerPager1" style="height: 50px"></div>

</div>