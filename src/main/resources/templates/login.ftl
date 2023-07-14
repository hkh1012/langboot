<!DOCTYPE html>
<html>
<head>
    <title>登录</title>
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/login.css" rel="stylesheet">
    <script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
    <script src="/static/login.js"></script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
        enabled. Please enable
        Javascript and reload this page!</h2></noscript>
<div id="main-content" class="container">
    <div class="row">
        <div class="col-md-12">
            <form class="form-inline" action="/auth/login" method="post">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" name="username" class="form-control" placeholder="请输入用户名">
                    <label for="username">密码</label>
                    <input type="password" id="password" name="password" class="form-control" placeholder="密码">
                </div>
                <button id="login" class="btn btn-default" type="submit">登录</button>
            </form>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <h4 id="errorMsg" style="color: red;">${errorMsg}</h4>
        </div>
    </div>
</div>
</body>
</html>