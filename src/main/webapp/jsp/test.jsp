<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="../boot/css/back.css">
    <link rel="stylesheet" href="../jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="../jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script type="text/javascript">



        $(function () {
            $("#dataTable").jqGrid(
                {
                    url : "${pageContext.request.contextPath}/json/a.json",
                    datatype : "json",
                    colNames : [ 'id', 'name', 'sex','url'],
                    colModel : [
                        {name : 'id',index : 'id',width : 55},
                        {name : 'name',index : 'invdate',width : 90,editable:true},
                        {
                            name: 'sex', index: 'name asc, invdate', width: 100, editable: true,
                            formatter:function (cellVale,option,row) {
                                if (cellVale == "nan") {
                                    return "<button class='btn btn-warning'>男</button>"
                                }
                                else {
                                    return "<button class='btn btn-warning'>女</button>"
                                }


                            }
                        },
                        {name : 'url',index : 'name asc, invdate',width : 100,editable:true,edittype:"file",
                            editoptions:{enctype:"multipart/form-data"},
                            formatter:function () {

                                return "<img src='${pageContext.request.contextPath}/image/BlueDream_4k.jpg' style='width:100px;height:80px' >";

                            }
                        }

                    ],
                    rowNum : 5,
                    rowList : [ 10, 20, 30 ],
                    pager : '#pager',
                    sortname : 'id',
                    mtype : "get",
                    viewrecords : true,
                    sortorder : "desc",
                    caption : "JSON 实例",
                    styleUI:"Bootstrap",
                    autowidth:true,
                    Height:true



                });
            jQuery("#dataTable").jqGrid('navGrid', '#pager', {edit : true,add : true,del : true},
                {
                    closeAfterEdit:true
                },
                {
                    closeAfterAdd: true,
                    afterSubmit:function (response, postData) {
                        var bannerId =response.responseJSON.bannerId;
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/banner/upload",
                            type:"post",
                            dataType:"json",
                            data:{bannerId:bannerId},
                            fileElementId: "url",
                            success:function (data) {

                            }
                        })
                        return response.postData;
                    }
                },
                {
                    closeAfterDel: true
                }

            );
        })






    </script>
</head>
<body>
<div class="container">

<table id="dataTable"></table>
<div id="pager" style="height: 50px"></div>

</div>
</body>
</html>