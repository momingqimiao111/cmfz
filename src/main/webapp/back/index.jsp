<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!--引入jquery核心js-->
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <script src="${path}/echarts/echarts.min.js"></script>
    <script>
    </script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例


    $.post('${path}/user/queryEcharts',(result)=>{
        let data1;
        let data2;
        let myChart = echarts.init(document.getElementById('main'));
        $.map(result,(i,map)=>{
            if (map=='男'){
                data1 = i;
                console.log(data1);
            }else {
                data2 = i;
            }
            // 指定图表的配置项和数据
            let option = {
                title: {
                    text: 'ECharts 入门示例'
                },
                tooltip: {},
                legend: {
                    data:['男','女']
                },
                xAxis: {
                    data: ["1天","七天","一月","一年"]
                },
                yAxis: {

                },
                series: [{
                    name: '男',
                    type: 'bar',
                    data: data1,
                },{
                    name: '女',
                    type: 'bar',
                    data: data2,
                }]
            };
            myChart.setOption(option);
        });

    },'JSON');



    // 使用刚指定的配置项和数据显示图表。





</script>


</body>
</html>