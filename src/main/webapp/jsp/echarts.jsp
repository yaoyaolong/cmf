<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '用户活跃度统计'
        },
        tooltip: {},
        legend: {
            data:['男','女']
        },
        xAxis: {
            data: ["一天","七天","一个月","一年"]
        },
        yAxis: {},


    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url:"${pageContext.request.contextPath}/user/ShwoAll",
        type:"post",
        datatype:"json",
        success:function (data) {
            myChart.setOption({
                series: [{
                    name: '男',
                    type: 'bar',
                    data: data.man
                },
                    {
                        name: '女',
                        type: 'bar',
                        data: data.woman
                    }],

            });
        }
    })
</script>
</body>