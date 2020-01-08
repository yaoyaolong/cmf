<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="../kindeditor/kindeditor-all-min.js"></script>
    <script src="../kindeditor/lang/zh-CN.js"></script>
    <script src="../echarts/echarts.min.js"></script>
    <script src="../echarts/china.js"></script>
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


    </script>
</head>
<body>


<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">持明法州后台管理系统</a>
        </div>
        <div>
            <!--向左对齐-->

            <!--向右对齐-->
            <ul class="nav navbar-nav navbar-right">
              <li><a>欢迎登陆</a></li>
              <li><a>退出</a></li>
            </ul>

        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-xs-2">

            <div class="panel-group" id="accordion">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseOne">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse ">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript: $('#centerLay').load('${pageContext.request.contextPath}/jsp/user.jsp')">用户管理</a></li>
                                <li><a href="javascript: $('#centerLay').load('${pageContext.request.contextPath}/jsp/echarts.jsp')">用户活跃度分析</a></li>
                                <li><a href="javascript: $('#centerLay').load('${pageContext.request.contextPath}/jsp/china.jsp')">用户地区分布</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo">
                               上师管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse">
                        <div class="panel-body">
                            <li><a href="javascript: $('#centerLay').load('${pageContext.request.contextPath}/jsp/guru.jsp')">上师管理</a></li>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseThree">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript: $('#centerLay').load('${pageContext.request.contextPath}/jsp/article.jsp')">文章管理</a></li>

                            </ul>
                        </div>
                    </div>
                </div>
                <div class="panel panel-warning">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"
                               href="#collapseFour">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapseFour" class="panel-collapse collapse">
                        <div class="panel-body">
                            <ul class="nav">
                            <li><a href="javascript: $('#centerLay').load('${pageContext.request.contextPath}/jsp/album.jsp')">专辑管理</a></li>

                                </ul>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion"

                               href="#collapsef">
                               轮播图管理
                            </a>
                        </h4>
                    </div>
                    <div id="collapsef" class="panel-collapse collapse ">
                        <div class="panel-body">
                            <ul class="nav">
                                <li><a href="javascript: $('#centerLay').load('${pageContext.request.contextPath}/jsp/banner.jsp')">轮播图管理</a></li>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>


        </div>
        <div class="col-xs-10" id="centerLay">
            <div class="jumbotron" >
                <div class="container">
                    <h3>欢迎光临持明法州后台管理系统</h3>
                </div>
                <div id="myCarousel" class="carousel slide">
                    <!-- 轮播（Carousel）指标 -->
                    <ol class="carousel-indicators">
                        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                        <li data-target="#myCarousel" data-slide-to="1"></li>
                        <li data-target="#myCarousel" data-slide-to="2"></li>
                    </ol>
                    <!-- 轮播（Carousel）项目 -->
                    <div class="carousel-inner">
                        <div class="item active">
                            <img src="../image/BlueDream_4k.jpg" alt="First slide">
                            <div class="carousel-caption">标题 1</div>
                        </div>
                        <div class="item">
                            <img src="../image/BlueDream_1080.jpg" alt="Second slide">
                            <div class="carousel-caption">标题 2</div>
                        </div>
                        <div class="item">
                            <img src="../image/oasis_4k.jpg" alt="Third slide">
                            <div class="carousel-caption">标题 3</div>
                        </div>
                    </div>
                    <!-- 轮播（Carousel）导航 -->
                    <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>

            </div>


        </div>
    </div>

</div>

<div class="panel-footer">
    <h4 style="text-align: center">@baizihjiaoyu @A123>com</h4>
</div>

<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header ">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">文章信息</h4>
            </div>
            <div class="modal-body">

                <form role="form" id="kindForm">
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
                            <option value="0">请选择</option>
                            <option value="1">展示</option>
                            <option value="2">冻结</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="guruId">上师</label>
                        <select class="form-control" id="guruId" name="guruId">

                        </select>
                    </div>

                </form>




                </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="sub()">提交更改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

</body>
