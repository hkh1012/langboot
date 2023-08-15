function contentChange(){
    let message = '';
    let content = $("#classicContent").val();
    let length = content ? content.length : 0;
    let max = 3000;
    message = '当前文本长度为' + length + '，剩余字数：' + (max - length);
    $("#classicHelp").html(message);
    $("#classicResult").html("");
}

function textClassic(){
    let content = $("#classicContent").val();
    let categoryList = $("#categoryList").val();
    $("#classicBtn").attr("disabled","disabled");
    $.post("/completion/classic",{"content":content,"categoryList":categoryList},function (d) {
        console.log(d);
        $("#classicBtn").removeAttr("disabled");
        if (d.code == '200'){
            let data = d.data;
            let categories = data.split(';');
            let _html = '';
            for (let i = 0; i < categories.length; i++) {
                _html += '<a href="#" class="btn btn-outline-cyan btn-round" style="margin-right: 10px;margin-bottom: 10px;">'+ categories[i].trim() +'</a>';
            }
            $("#classicResult").html(_html);
        }
    },"json");
}