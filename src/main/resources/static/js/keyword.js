function contentChange(){
    let message = '';
    let content = $("#keywordContent").val();
    let length = content ? content.length : 0;
    let max = 3000;
    message = '当前文本长度为' + length + '，剩余字数：' + (max - length);
    $("#keywordHelp").html(message);
    $("#keywordResult").html("");
}

function genKeywords(){
    let content = $("#keywordContent").val();
    let keywordArea = $("#keywordArea").val();
    let keywordNum = $("#keywordNum").val();
    console.log(keywordNum);
    if (!keywordNum){
        keywordNum = 3;
    }
    $("#genKeywordsBtn").attr("disabled","disabled");
    $.post("/completion/keyword",{"content":content,"keywordArea":keywordArea,"keywordNum":keywordNum},function (d) {
        console.log(d);
        $("#genKeywordsBtn").removeAttr("disabled");
        if (d.code == '200'){
            let data = d.data;
            let keywords = data.split(';');
            let _html = '';
            for (let i = 0; i < keywords.length; i++) {
                _html += '<a href="#" class="btn btn-outline-cyan btn-round" style="margin-right: 10px;margin-bottom: 10px;">'+ keywords[i].trim() +'</a>';
            }
            $("#keywordResult").html(_html);
        }
    },"json");
}