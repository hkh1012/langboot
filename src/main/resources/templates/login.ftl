<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="applicable-device" content="pc,mobile">
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/login.css" rel="stylesheet">
    <script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
    <script src="/static/login.js"></script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
        enabled. Please enable
        Javascript and reload this page!</h2></noscript>
<div id="main-content">
    <div class="lunbo">

    </div>
    <div class="right-container">
        <div class="login-form">
            <div class="login-title">
                <h3>欢迎使用</h3>
            </div>
            <form action="/login" method="post">
                <div class="form-group">
                    <input type="text" id="username" name="username" class="form-control" placeholder="请输入用户名">
                    <input type="password" id="password" name="password" class="form-control" placeholder="密码">
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