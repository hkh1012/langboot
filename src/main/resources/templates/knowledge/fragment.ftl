<!DOCTYPE html>
<html>
<head>
    <title>知识片段列表</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="applicable-device" content="pc,mobile">
    <link href="/static/css/knowledge/fragment.css" rel="stylesheet"/>
    <script>
        let fragmentListPageNum = ${pageInfo.pageNum};
        let searchContent = '${formData.searchContent}';
        let formKid = '${formData.kid}';
        let formKname = '${formData.kname}';
        let formDocId = '${formData.docId}';
        let formDocName = '${formData.docName}';
    </script>
    <script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
    <script src="/static/js/common/confirm.js"></script>
    <script src="/static/js/knowledge/fragment.js"></script>

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
<#include "../common/topbar.ftl">
<main class="container" style="margin-top: 100px;">
    <div class="row">
        <div class="col">
            <h4 style="text-align: center;">知识片段管理【${knowledge.kname}】</h4>
        </div>
    </div>
    <div class="row" style="margin-top: -20px;margin-bottom: 10px;">
        <div class="col">
            <a href="/knowledge/index">&lt&lt返回知识库列表</a>
        </div>
    </div>
    <div class="row">
        <div class="col">
            <input type="text" class="form-control input-round" id="knowledge-fragment-search-content" value="${formData.searchContent}" placeholder="片段内容">
        </div>
        <div class="col">
            <button type="submit" class="btn btn-outline-success btn-round" onclick="searchKnowledgeFragment('${formData.kid}','${formData.docId}');">搜索</button>
        </div>
    </div>
    <div class="row" style="margin-top: 12px;margin-bottom: 12px;padding-left: 14px;">
        <a href="#" class="btn btn-success btn-round shadow-lg" onclick="addKnowledgeFragment();">新增</a>
    </div>

    <!-- table -->
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col" style="width: 120px;"> 知识库KID	</th>
            <th scope="col" style="width: 120px;"> 文档ID	</th>
            <th scope="col" style="width: 120px;"> 片段ID	</th>
            <th scope="col" style="width: 80px;"> 排序	</th>
            <th scope="col"> 知识片段	</th>
            <th scope="col" style="width: 100px;"> 操作	</th>
        </tr>
        </thead>
        <tbody>
            <#list pageInfo.list as item>
                <tr>
                    <td> ${item.kid} </td>
                    <td> ${item.docId} </td>
                    <td> ${item.fid} </td>
                    <td> ${item.idx} </td>
                    <td> ${item.content} </td>
                    <td>
                        <span class="btn btn-sm btn-outline-danger btn-round" onclick="removeKnowledgeFragment('${item.kid}','${item.fid}')">删除</span>
                    </td>
                </tr>
            </#list>
        </tbody>
    </table>
    <#import "../common/pagination.ftl" as fpage />
    <@fpage.fpage page=pageInfo.pageNum pagesize=pageInfo.pageSize totalpages=pageInfo.pages totalrecords=pageInfo.total url="/knowledge/fragment?kid=" + formData.kid + "&kname="+formData.kname+"&docId="+ formData.docId+"&docName=" +formData.docName + "&searchContent=" + formData.searchContent/>

</main>
<div class="fragment-container h">
    <div class="fragment-edit-form">
        <div class="fragment-edit-form-content">
            <div class="form-group">
                <div class="col col-flex-right">
                    <svg xmlns="http://www.w3.org/2000/svg" height="16" width="12" style="cursor: pointer;" viewBox="0 0 384 512" onclick="closeFormDiv();">
                        <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.-->
                        <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
                    </svg>
                </div>
            </div>
            <div class="form-group">
                <div class="col">
                    <h4>新增知识片段</h4>
                </div>
            </div>

        <div class="form-group">
            <div class="col">
                <textarea class="form-control" id="knowledge-fragment" rows="6" placeholder="知识片段"></textarea>
            </div>
        </div>
            <div class="form-group">
                <div class="col">
                    <h6 id="fragment-warning-msg" style="color: red;"></h6>
                </div>
            </div>
            <div class="form-group">
                <div class="col col-flex-right">
                    <button type="button" class="btn btn-outline-danger btn-round" onclick="closeFormDiv();">取消</button>
                    <button type="button" class="btn btn-success btn-round" style="margin-left: 10px;" onclick="saveKnowledgeFragment();">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>

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