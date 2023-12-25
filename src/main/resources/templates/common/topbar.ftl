<nav class="topnav navbar navbar-expand-lg navbar-dark bg-success fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="/index"><i class="fas fa-anchor mr-2"></i>
<#--            <strong>首页</strong>-->
        </a>
        <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarColor02" aria-controls="navbarColor02" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="navbar-collapse collapse" id="navbarColor02" style="">
            <ul class="navbar-nav mr-auto d-flex align-items-center">
                <li class="nav-item">
                    <a class="nav-link" href="/chat">对话</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/summary">摘要</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/translate">翻译</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/keyword">关键词</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        更多 </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="/classic">文本分类</a>
                        <a class="dropdown-item" href="/security">内容安全</a>
                        <a class="dropdown-item" href="/function">函数调用</a>
                        <a class="dropdown-item" href="/agent">agent代理</a>
                        <a class="dropdown-item" href="/sentiment">情感分析</a>
                        <a class="dropdown-item" href="/duplication">查重</a>
                        <a class="dropdown-item" href="/textToImage">文生图</a>
                    </div>
                </li>
<#--                <li class="nav-item">-->
<#--                    <a class="nav-link" href="./docs.html">Docs</a>-->
<#--                </li>-->
            </ul>
            <ul class="navbar-nav ml-auto d-flex align-items-center">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        ${sysUser.nickName} </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item" href="/knowledge/index" >知识库管理</a>
                        <a class="dropdown-item" href="/logout">退出系统</a>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>