<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>goeasy</title>
    <!--引入bootstrap css-->
    <link rel="stylesheet" href="${path}/boot/css/bootstrap.min.css">
    <!--引入jqgrid的bootstrap css-->
    <link rel="stylesheet" href="${path}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <!--引入jquery核心js-->
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <!--引入bootstrap组件js-->
    <script src="${path}/boot/js/bootstrap.min.js"></script>
    <!--引入jqgrid核心js-->
    <script src="${path}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <!--引入jqgrid国际化js-->
    <script src="${path}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${path}/boot/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        const userId = "刘晋陆";
        let goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-7678885b4d8443f6abe98b0fe45edaf2", //替换为您的应用appkey

            onConnected: function () {

                $('#dd').append('<div style="text-align: center"><span  >Connect to GoEasy success！</span></div>');

                console.log("Connect to GoEasy success.");

            },
            onDisconnected: function () {
                console.log("Disconnect to GoEasy server.");

            },
        onConnectFailed: function (error) {
            console.log("Connect to GoEasy failed, Error code: "+error.code+" Error message: "+error.content);

        }
        });
        goEasy.subscribe({
            channel: "cmfz", //替换为您自己的channel
            onMessage: function (message) {
                // 手动将 字符串类型转换为 Json类型
                let data = JSON.parse(message.content);
                if (data.id == userId){
                    $('#dd').append('<div style="margin-top: 5px;" ><div style="text-align: right">'+data.id+'</div><div style="text-align: right"><span class="label label-success">'+data.context+'</span></div></div>');
                }
                else{
                    $('#dd').append('<div style="margin-top: 5px;" ><div style="text-align: left">'+data.id+'</div><div style="text-align: left"><span class="label label-success">'+data.context+'</span></div></div>');
                }

                let div = document.getElementById("dd");
                div.scrollTop = div.scrollHeight;
               // alert("Channel:" + message.channel + " content:" + message.content);
            },
            onSuccess: function () {

                console.log("Subscribe the Channel successfully.");
            },
            onFailed: function (error) {
                console.log("Subscribe the Channel failed, error code:" + error.code + " error message: " + error.content);
            }
        });
            //发送
        function submitText() {
            let text =JSON.stringify({
                id:userId,
                context:$('#context').val()
            });

            goEasy.publish({
                channel: "cmfz",
                message: text,
                onSuccess:function(){
                    console.log("发送成功");
                    $('#context').val('');
                },
                onFailed: function (error) {
                    console.log("Publish message failed, Error code："+error.code+" Error message："+error.content);
                }
            });
        }
        $(function () {
            $("body").keyup(function(e){

                if (e.key =="Enter"&&$('#context').val()){
                    submitText();

                }
            });
        });


    </script>
</head>
<body>

<h1 class="text-center">聊天室</h1>

<div class="container">
    <div class="row">
        <div class="col-sm-8 col-sm-offset-2" style="height: 500px;border: 1px solid black;overflow: auto;" id="dd">

        </div>

    </div>

    <div class="row">
        <div class="col-sm-8 col-sm-offset-2">
            <div class="input-group">
                <input type="text" class="form-control" id="context" placeholder="">
                <span class="input-group-btn">
        <button class="btn btn-primary" type="button" onclick="submitText()">发送</button>
      </span>
            </div><!-- /input-group -->
        </div>
    </div>
</div>


</body>
</html>