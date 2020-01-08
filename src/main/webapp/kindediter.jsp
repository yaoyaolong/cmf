<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="./boot/css/back.css">
    <link rel="stylesheet" href="./jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="./jqgrid/css/jquery-ui.css">
    <script src="./boot/js/jquery-2.2.1.min.js"></script>
    <script src="./boot/js/bootstrap.min.js"></script>
    <script src="./jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="./jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="./boot/js/ajaxfileupload.js"></script>
    <script src="./kindeditor/kindeditor-all-min.js"></script>
    <script src="./kindeditor/lang/zh-CN.js"></script>
    <script type="text/javascript" >
        KindEditor.ready(function(K) {
            window.editor = K.create('#editor_id',{
                uploadJson : '${pageContext.request.contextPath}/article/uploadImg',
                fileManagerJson : '${pageContext.request.contextPath}/article/showAllImg',
                allowFileManager : true,
               afterBlur:function () {
                    this.sync();
                }

            });
        });
        $.ajax({
            url:"${pageContext.request.contextPath}/guru/showALLGuru",
            type:"post",
            datatype:"json",
            success:function (data) {
                var option=" ";
                    data.forEach(function (guru) {
                        option +="<option value="+guru.id+">"+guru.name+"</option>"
                    })
                $("#guruId").html(option)

            }
        })
        function sub() {


            $.ajaxFileUpload({

                url:"${pageContext.request.contextPath}/article/insert",
                type: "post",
                data: {"id":$('#id').val(),"title":$('#name').val(),"content":$("#editor_id").val(),"status":$('#status').val(),"guruId":$('#guruId').val()},
                datatype: "json",
                fileElementId:"inputfile",
                success:function (data) {

                }

            })
        }
    </script>

</head>
<body>


<form role="form">
    <div class="form-group">

        <input type="hidden" class="form-control" id="id" name="id" placeholder="请输入名称">
    </div>
    <div class="form-group">
        <label for="name">标题</label>
        <input type="text" class="form-control" id="name" name="title" placeholder="请输入名称">
    </div>
    <div class="form-group">
        <label for="inputfile">封面</label>
        <input type="file" id="inputfile" name="inputfile">

    </div>

    <div class="form-group">
        <label for="editor_id">内容</label>
        <textarea id="editor_id" name="content" style="width:700px;height:300px;">
&lt;strong&gt;HTML内容&lt;/strong&gt;
</textarea>
    </div>

    <div class="form-group">
        <label for="status">状态</label>
        <select class="form-control" id="status" name="status">
            <option value="1">展示</option>
            <option value="2">冻结</option>
        </select>
    </div>

    <div class="form-group">
        <label for="guruId">上师</label>
        <select class="form-control" id="guruId" name="guruId">

        </select>
    </div>

    <button type="button" class="btn btn-default" onclick="sub()">提交</button>
</form>




</body>
