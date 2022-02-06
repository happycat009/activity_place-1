<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.js"></script>
</head>
<body>
    <button id="btn">点击发送ajax</button>
    <button id="btn2">点我发送用jsonp解决跨域问题</button>
    <script>
        /*跨域，不能通过请求*/
        $("#btn").click(function (){
            $.ajax({
                type:"GET",
                url:"http://192.168.43.112:8080/user/getUser",
                dataType:"json",
                success:function (resp) {
                    alert(resp.name+"==="+resp.age);
                }
            })
        })

        $("#btn2").click(function (){
            $.ajax({
                type:"GET",
                url:"http://192.168.43.112:8080/admin/getUser",
                dataType:"jsonp",
                jsonp:"callback",
                jsonpCallback:"methods",
                success:function (resp) {
                    console.log(resp);
                    alert(resp.name+"==="+resp.age);
                },
            })
        })
        function methods(resp) {
            console.log(resp);
            alert(resp.name);
        }
    </script>
</body>
</html>