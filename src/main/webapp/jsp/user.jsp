<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#userTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/user/queryByPage",
                datatype : "json",
                colNames : [ 'id', 'phone', 'password', 'status', 'photo','name','nickName','sex','location','rigestDate'],
                colModel : [
                    {name : 'id',align:"center",hidden: true},
                    {name : 'phone',align:"center",editable: true,editrules:{required:true}},
                    {name : 'password',align:"center",editable: true,editrules:{required:true}},
                    {name : 'status',align : "center",editable:true,editrules:{required:true},edittype:"select",editoptions:{value:"1:展示;0:冻结"},
                        formatter:function(cellValue,option,row){
                            if(cellValue==1){
                                //展示
                                return "<button class='btn btn-warning' onclick='updateStatus(\""+row.id+"\",\""+cellValue+"\")' >展示</button>";
                            }else{
                                //不展示
                                return "<button class='btn btn-success' onclick='updateStatus(\""+row.id+"\",\""+cellValue+"\")' >冻结</button>";
                            }
                        }


                    },
                    {name : 'photo',align:"center",edittype:"file",editoptions:{enctype:"multipart/form-data"},editable: true,
                        formatter:function(data){
                            return "<img src='${pageContext.request.contextPath}"+data+"' style='width:100px;height:80px' >";
                        }
                    },
                    {name : 'name',align:"center",editable:true},
                    {name : 'nickName',align:"center",editable:true,editrules:{required:true}

                    },
                    {name : 'sex',align:"center" ,editable:true,editrules:{required:true},edittype:"select",editoptions:{value: "0:男;1:女"},
                        formatter:function (data) {
                            if(data=="0"){
                                return "男"
                            }else {
                                return "女"
                            }

                        }
                    },
                    {name : 'location',align:"center" ,editable:true,editrules:{required:true} },
                    {name : 'rigestDate',align:"center" ,editable:true,editrules:{required:true},edittype:"date" },

                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#userPager',
                sortname : 'id',
                mtype : "post",
                viewrecords : true,
                sortorder : "desc",

               styleUI:"Bootstrap",
                autowidth:true,
               /* multiselect:true,*/
               height: "400px",

                editurl:"${pageContext.request.contextPath}/user/edit"

            });
        jQuery("#userTable").jqGrid('navGrid', '#userPager', {edit : true,add : true,del : true,edittext:"编辑",addtext: "添加",deltext:"删除"},
            {
                closeAfterEdit:true,
                afterSubmit: function (response,postData) {
                    var userId=response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/upload",
                        type:"post",
                        datatype: "json",
                        data:{userId :userId},
                        fileElementId:"photo",
                        success:function (data) {
                            $("#userTable").trigger("reloadGrid");
                        }




                    });
                    return postData;

                }
            },
            {
                closeAfterAdd: true,
                afterSubmit: function (response,postData) {
                    var userId=response.responseJSON.userId;
                    $.ajaxFileUpload({
                        url:"${pageContext.request.contextPath}/user/upload",
                        type:"post",
                        datatype: "json",
                        data:{userId :userId},
                        fileElementId:"photo",
                        success:function (data) {
                                $("#userTable").trigger("reloadGrid");
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
    <h4>用户管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a >用户详情</a></li>




</ul>

<div id="loggs">

<table id="userTable"></table>
<div id="userPager" style="height: 50px"></div>

</div>