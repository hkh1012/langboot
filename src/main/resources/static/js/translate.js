function contentChange(){
    let message = '';
    let content = $("#translateContent").val();
    let length = content ? content.length : 0;
    let max = 3000;
    message = '当前文本长度为' + length + '，剩余字数：' + (max - length);
    $("#translateHelp").html(message);
    $("#translateResult").html("");
}

function beginTranslate(){
    let content = $("#translateContent").val();
    let targetLanguage = $("#targetLanguage").val();
    $("#translateBtn").attr("disabled","disabled");
    $.post("/completion/translate",{"content":content,"targetLanguage":targetLanguage},function (d) {
        console.log(d);
        $("#translateBtn").removeAttr("disabled");
        if (d.code == '200'){
            let data = d.data;
            $("#translateResult").html(data);
        }
    },"json");
}