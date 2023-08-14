<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="applicable-device" content="pc,mobile">
<#--    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">-->
    <link href="/static/css/index.css" rel="stylesheet">
    <script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
    <script src="/static/js/index.js"></script>

    <!-- Fonts -->
    <#--    <link href="https://fonts.googleapis.com/css?family=Nunito:300,300i,400,600,700" rel="stylesheet">-->
    <link href="/static/css/googlefont.css" rel="stylesheet">
    <link href="/static/css/all.css" rel="stylesheet">
    <#--    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">-->

    <!-- CSS -->
    <link href="/static/anchor/assets/css/main.css" rel="stylesheet"/>
    <link href="/static/anchor/assets/css/vendor/aos.css" rel="stylesheet"/>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
        enabled. Please enable
        Javascript and reload this page!</h2></noscript>
<#include "topbar.ftl">


<!-------------------------------------
HEADER
--------------------------------------->
<header>
    <div class="jumbotron jumbotron-lg jumbotron-fluid mb-0 pb-3 bg-success position-relative">
        <div class="container-fluid text-white h-100">
            <div class="d-lg-flex align-items-center justify-content-between text-center pl-lg-5">
                <div class="col pt-4 pb-4">
                    <h1 class="display-3">Build ai applications <strong>nice</strong> & easy</h1>
                    <h5 class="font-weight-light mb-4">With Langchain-Springboot <strong> free</strong>
<#--                        & <strong><i class="fab fa-sass fa-2x text-info"></i></strong>-->
                    </h5>
                    <a href="https://github.com/hkh1012/langchain-springboot" class="btn btn-lg btn-outline-white btn-round">Learn more</a>
                </div>
<#--                <div class="col align-self-bottom align-items-right text-right h-max-380 h-xl-560 position-relative z-index-1">-->
<#--                    <img src="/static/anchor/assets/img/demo/dashb.png" class="rounded img-fluid">-->
<#--                </div>-->
            </div>
        </div>
    </div>
    <svg style="-webkit-transform:rotate(-180deg); -moz-transform:rotate(-180deg); -o-transform:rotate(-180deg); transform:rotate(-180deg); margin-top: -1px;enable-background:new 0 0 1440 126;" version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewbox="0 0 1440 126" xml:space="preserve">
    <path class="bg-success" d="M685.6,38.8C418.7-11.1,170.2,9.9,0,30v96h1440V30C1252.7,52.2,1010,99.4,685.6,38.8z"/>
    </svg>
</header>
<!--- END HEADER -->

<main class="container">
    <!--------------------------------------
CARDS
--------------------------------------->
    <section class="pt-4 pb-5" data-aos="fade-up">
        <h3 class="h5 mb-4 font-weight-bold">功能列表</h3>
        <div class="row">
            <div class="col-lg-6 bigCard">
                <div class="card bg-dark overlay overlay-black text-white shadow-sm border-0">
                    <img class="card-img" src="/static/anchor/assets/img/demo/5.jpg" alt="Card image">
                    <div class="card-img-overlay d-flex align-items-center text-center">
                        <div class="card-body">
                            <h3 class="card-title">人机对话</h3>
                            <p class="card-text">
                                利用LLM大模型对话能力，实现流式，结合本地知识库，结果更精准。
                            </p>
                            <a href="/chat" class="btn btn-info btn-round">Stream Chat</a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-6">
                <div class="card bg-dark overlay overlay-blue text-white shadow-sm border-0 mt-sm-4 mt-lg-0">
                    <img class="card-img" src="/static/anchor/assets/img/demo/9.jpg" alt="Card image">
                    <div class="card-img-overlay d-flex align-items-center text-center">
                        <div class="card-body">
                            <h3 class="card-title">文本摘要</h3>
                            <p class="card-text">
                                解放你的阅读时间，尽览全文精华。
                            </p>
                            <a href="/summary" class="btn btn-purple btn-round">Summary</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mt-4">
            <div class="col-md-4">
                <div class="card shadow-sm border-0">
                    <img class="card-img-top" src="/static/anchor/assets/img/demo/2.jpg" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">翻译</h5>
                        <p class="card-text text-muted">
                            英译汉，汉译英，NLP，让语言无界限，实现自然语言翻译的奇迹！
                        </p>
                        <a href="/translate" class="btn btn-info btn-round">Translate</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm border-0">
                    <img class="card-img-top" src="/static/anchor/assets/img/demo/blog8.jpg" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">文本分类</h5>
                        <p class="card-text text-muted">
                            文字经典，智能分类引领未来
                        </p>
                        <a href="#" class="btn btn-purple btn-round">Classic</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm border-0">
                    <img class="card-img-top" src="/static/anchor/assets/img/demo/10.jpg" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">关键词提取</h5>
                        <p class="card-text text-muted">
                            关键词提取，高效推动内容优化。
                        </p>
                        <a href="/keyword" class="btn btn-warning btn-round">Keyword</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-5">
            <div class="col-md-4">
                <div class="card shadow-sm border-0">
                    <img class="card-img-top" src="/static/anchor/assets/img/demo/1.jpg" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">情感分析</h5>
                        <p class="card-text text-muted">
                            一句话，读懂千言情。NLP情感分析帮你轻松搞定。
                        </p>
                        <a href="#" class="btn btn-cyan btn-round">Sentiment Analysis</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm border-0">
                    <img class="card-img-top" src="/static/anchor/assets/img/demo/4.jpg" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">查重</h5>
                        <p class="card-text text-muted">
                            依托NLP算法，实现精准查重，防止知识泄漏！
                        </p>
                        <a href="/keyword" class="btn btn-danger btn-round">Duplication Check</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card shadow-sm border-0">
                    <img class="card-img-top" src="/static/anchor/assets/img/demo/3.jpg" alt="Card image cap">
                    <div class="card-body">
                        <h5 class="card-title">文生图</h5>
                        <p class="card-text text-muted">
                            创意无限，灵感永恒，文生图陪伴你探索无尽的创造之路
                        </p>
                        <a href="/translate" class="btn btn-secondary btn-round">Text To Image</a>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>

<script src="/static/anchor/assets/js/vendor/jquery.min.js" type="text/javascript"></script>
<script src="/static/anchor/assets/js/vendor/popper.min.js" type="text/javascript"></script>
<script src="/static/anchor/assets/js/vendor/bootstrap.min.js" type="text/javascript"></script>
<script src="/static/anchor/assets/js/functions.js" type="text/javascript"></script>

<!-- Animation -->
<script src="/static/anchor/assets/js/vendor/aos.js" type="text/javascript"></script>
<noscript>
    <style>
        *[data-aos] {
            display: block !important;
            opacity: 1 !important;
            visibility: visible !important;
        }
    </style>
</noscript>
<script>
    AOS.init({
        duration: 700
    });
</script>

<!-- Disable animation on less than 1200px, change value if you like -->
<script>
    AOS.init({
        disable: function () {
            var maxWidth = 1200;
            return window.innerWidth < maxWidth;
        }
    });
</script>

<!-- Carousel Height Smooth -->
<script>
    $('.carousel').on('slide.bs.carousel', function (event) {
        var height = $(event.relatedTarget).height();
        var $innerCarousel = $(event.target).find('.carousel-inner');
        $innerCarousel.animate({
            height: height
        });
    });
</script>

<!-- Popovers -->
<script>
    $(function () {
        $('[data-toggle="popover"]').popover()
    })
    $('.popover-dismiss').popover({
        trigger: 'focus'
    })
</script>

<!-- Tooltips -->
<script>
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })
</script>
</body>
</html>