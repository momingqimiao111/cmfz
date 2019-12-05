<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
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
                    text: '用户注册时间统计表'
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
</script>
<div class="col-sm-10">
    <div id="main" style="width: 600px;height:400px;"></div>
</div>
