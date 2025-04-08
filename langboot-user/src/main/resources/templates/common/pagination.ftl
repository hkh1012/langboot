<!-- pagination -->

<#--
    参数解释：
    page：当前页码，从1开始
    pagesize:分页大小
    totalpages：总页数。也可以放到模板里计算。这里在MyPager类里已计算过了。
    totalrecords：总记录数
    url：链接地址，自动拼接page参数。为了简单，没有判断当前页面是否有get参数了，直接做了&连接，默认认为原来就有参数。当前你也可以用servlet自己获取当前url及参数，就不用传了。

    以上这些参数除url外均可以从MyPager实例中获取。其实可以直接把mypager实例传进来，为了便于以后扩展，分字段传过来。
-->
<#macro fpage page pagesize totalpages totalrecords url>
    <style>
        .page-item.active .page-link {
            z-index: 1;
            color: #fff;
            background-color: #44c553;
            border-color: #44c553;
        }

        .pagination {
            display: flex;
            padding-left: 0;
            list-style: none;
            border-radius: 0.25rem;
            flex-direction: row;
            /* align-content: flex-start; */
            justify-content: flex-end;
        }

        .pagination .page-text{
            margin: auto 10px auto;
        }
    </style>
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <span class="page-text">共${totalrecords}条记录  第${page}页/共${totalpages}页</span>
        <#--startpage:起始页码就是page，endpage:结束页码，showfirstpage是否显示首页按钮，showlastpage：是否显示末页按钮，showpre是否显示前...，shownext是否显示后...-->
            <#assign startpage = page,endpage=10,showfirstpage=false,showlastpage=false,showpre=false,shownext=false,prepage = 1,nextpage=11>
        <#--是否显示首页按钮及计算初始页码-->
            <#if page gt 1>
                <#assign showfirstpage = true>
            <#--startpage向前挪4页，如果不足4页，则startpage=1-->
                <#assign startpage=(page-4)>
                <#if startpage lte 0>
                    <#assign startpage = 1>
                </#if>
            </#if>
        <#--是否显示前n页的...，以及...的链接-->
            <#if page gt 5>
                <#assign showpre = true,prepage=page-5>
            </#if>
        <#--计算endpage-->
            <#if page+pagesize-1 lt totalpages>
                <#assign endpage = page+pagesize-1>
            <#--显示后面的...按钮-->
                <#assign shownext = true>
            <#--后面...的页面码-->
                <#assign nextpage=page+pagesize>
            <#--显示末页-->
                <#assign showlastpage = true>
            <#else>
                <#assign endpage = totalpages>
            </#if>
            <#if endpage lte 0>
                <#assign endpage = 1>
            </#if>

        <#--开始展示-->

        <#--首页-->
            <#if showfirstpage>
                <li class="page-item"><a class="page-link" href="${url}&pageNum=1">首页</a></li>
            </#if>
        <#--前面的...-->
            <#if showpre>
                <li class="page-item"><a class="page-link" href="${url}&pageNum=${prepage}">...</a></li>
            </#if>
        <#--显示的页码按钮-->
            <#list startpage..endpage as p>
                <li  class="page-item <#if p == page>active</#if>"><a class="page-link" href="${url}&pageNum=${p}">${p}</a></li>
            </#list>
        <#--后面的...-->
            <#if shownext>
                <li class="page-item"><a class="page-link" href="${url}&pageNum=${nextpage}">...</a></li>
            </#if>
        <#--显示尾页-->
            <#if showlastpage>
                <li class="page-item"><a class="page-link" href="${url}&pageNum=${totalpages}">末页</a></li>
            </#if>
        </ul>
    </nav>
</#macro>