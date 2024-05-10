function contentChange(){
    let message = '';
    let content = $("#securityContent").val();
    let length = content ? content.length : 0;
    let max = 3000;
    message = '当前文本长度为' + length + '，剩余字数：' + (max - length);
    $("#securityHelp").html(message);
    $("#securityResult").html("");
}

function securityCheck(){
    let content = $("#securityContent").val();
    $("#securityBtn").attr("disabled","disabled");
    $.post("/completion/security",{"content":content},function (d) {
        console.log(d);
        $("#securityBtn").removeAttr("disabled");
        if (d.code == '200'){
            let data = d.data;
            let _html = '';
            if (data.trim().indexOf("安全")>-1) {
                _html += '<a href="#" class="btn btn-outline-success btn-round" style="margin-right: 10px;margin-bottom: 10px;">'+ '安全' +'</a>';
            }else {
                _html += '<a href="#" class="btn btn-outline-danger btn-round" style="margin-right: 10px;margin-bottom: 10px;">'+ '危险' +'</a>';
            }
            $("#securityResult").html(_html);
        }
    },"json");
}