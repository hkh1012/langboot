function contentChange(){
    let message = '';
    let content = $("#functionContent").val();
    let length = content ? content.length : 0;
    let max = 3000;
    message = '当前文本长度为' + length + '，剩余字数：' + (max - length);
    $("#functionHelp").html(message);
    $("#functionResult").html("");
}

function functionCheck(){
    let content = $("#functionContent").val();
    $("#functionBtn").attr("disabled","disabled");
    $.post("/completion/function/weather",{"content":content},function (d) {
        console.log(d);
        $("#functionBtn").removeAttr("disabled");
        if (d.code == '200'){
            let data = d.data;
            $("#functionResult").html(data);
        }
    },"json");
}