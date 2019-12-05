<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="${path}/boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="${path}/boot/js/jquery-2.2.1.min.js"></script>
    <script>

           function login() {
               $.post('${path}/admin/login',{username:$('#username').val(),password:$('#password').val(),code:$('#code').val()},(result)=>{

                  if (result=='success'){
                     window.location.href='${path}/back/main.jsp';
                  }else{
                      $('#msg').text(result);

                  }
               });
           }


    </script>
</head>
<body style=" background: url(${path}/img/980186345982b2b7bcce9fcb3cadcbef76099b35.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>

        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="请输入用户名" autocomplete="off" name="username" id="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="请输入密码" autocomplete="off" name="password" id="password">
            </div>
            <div class="form-group">
                <div class="row">
                    <div class="col-sm-2">

                            <img src="${path}/admin/getCode"  id="codeImg" onclick="document.getElementById('codeImg').src = '${path}/admin/getCode?'+(new Date()).getTime()">


                    </div>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" placeholder="请输入验证码" autocomplete="off" id="code">
                    </div>
                </div>

            </div>
            <span id="msg" style="color: red;"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" class="btn btn-primary form-control" id="log" onclick="login()">登录</button>
            </div>

        </div>
    </div>
</div>
</body>
</html>
