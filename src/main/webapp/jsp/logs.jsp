<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function () {
    jQuery("#bannerTable").jqGrid(
        {
            url : '${pageContext.request.contextPath}/loggs/select',
            datatype : "json",
            colNames : [ 'id', 'name', 'time', 'method' ],
            colModel : [
                {name : 'id',align : "center"},
                {name : 'name',align : "center"},
                {name : 'time',align : "center"},
                {name : 'method',align : "center"},

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
            height:"500px"

        });
    jQuery("#bannerTable").jqGrid('navGrid', '#bannerPager', {edit : false,add : false,del : false});
})

</script>

<div id="loggs">

<table id="bannerTable"></table>
<div id="bannerPager" style="height: 50px"></div>

</div>