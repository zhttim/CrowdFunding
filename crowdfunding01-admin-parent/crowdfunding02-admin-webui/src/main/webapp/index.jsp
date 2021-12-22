<%--
  Created by IntelliJ IDEA.
  User: tim
  Date: 2021/12/22
  Time: 1:44 下午
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="webjars/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn1").click(function () {
                // 准备要发送的数组
                let array = [5, 8, 12];
                // 将 JSON 数组转换成 JSON 字符串
                let requestBody = JSON.stringify(array);
                $.ajax({
                    "url": "send/array1.json",
                    "type": "post",
                    // JSON 字符串作为请求体
                    "data": requestBody,
                    // 告诉服务器端当前请求的请求体是 JSON 格式
                    contentType: "application/json;character=UTF-8",
                    "dataType": "json",
                    "success": function (response){
                       console.log(response);
                    },
                    "error": function (response){
                        console.log(response);
                    }
                })
            });
        });
    </script>
</head>
<body>
<a href="test/ssm.html">ssm页面测试</a> <br>

<button id="btn1">send[5,8,12]</button>
</body>
</html>
