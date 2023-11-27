<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="applicable-device" content="pc,mobile">
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/login.css" rel="stylesheet">
    <script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
    <script src="/static/js/login.js"></script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
        enabled. Please enable
        Javascript and reload this page!</h2></noscript>
<div id="main-content">
    <div class="lunbo">
<#--        <img src="/static/img/illustration3.png">-->
    </div>
    <div class="right-container">
        <div class="login-form">
            <div class="login-title">
                <h3>欢迎使用</h3>
            </div>
            <form action="/login" method="post">
                <div class="form-group">
                    <input type="text" id="username" name="username" class="form-control" placeholder="用户名demo">
                    <input type="password" id="password" name="password" class="form-control" placeholder="密码123456">
                </div>
                <button id="login" class="btn btn-success btn-lg btn-block" type="submit">登  录</button>
            </form>
            <div class="row">
                <div class="col-md-12">
                    <h5 id="errorMsg" style="color: red;">${errorMsg}</h5>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>