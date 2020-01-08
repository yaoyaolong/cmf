<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    $(function () {
        $("#guruTable").jqGrid(
            {
                url : "${pageContext.request.contextPath}/guru/show",
                datatype : "json",
                colNames : [ 'id', 'name', 'photo', 'status', 'nickName' ],
                colModel : [
                    {name : 'id',align:"center",hidden: true},
                    {name : 'name',align:"center",editable: true,editrules:{required:true}},
                    {name : 'photo',align:"center",edittype:"file",editoptions:{enctype:"multipart/form-data"},editable: true,
                        formatter:function(data){
                            return "<img src='${pageContext.request.contextPath}"+data+"' style='width:100px;height:80px' >";
                        }
                    },
                    {name : 'status',align:"center",editable:true,
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
                    {name : 'nickName',align:"center",

                    },


                ],
                rowNum : 10,
                rowList : [ 10, 20, 30 ],
                pager : '#guruPager',
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
        jQuery("#guruTable").jqGrid('navGrid', '#guruPager', {edit : false,add : false,del : false,edittext:"编辑",addtext: "添加",deltext:"删除"},
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
function updateStatus(id,value) {

        $.ajax({
            url:"${pageContext.request.contextPath}/guru/updateStatus",
            type:"post",
            datatype:"json",
            data:{"id":id,"value":value},
            success:function (data) {
                $("#guruTable").trigger("reloadGrid");
            }

        })

}

</script>
<div class="page-header">
    <h4>上师管理</h4>
</div>
<ul class="nav nav-tabs">
    <li><a >上师详情</a></li>




</ul>

<div id="loggs">

<table id="guruTable"></table>
<div id="guruPager" style="height: 50px"></div>

</div>