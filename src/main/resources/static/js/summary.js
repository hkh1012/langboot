function contentChange(){
    let message = '';
    let content = $("#summariedContent").val();
    let length = content ? content.length : 0;
    let max = 3000;
    message = '当前文本长度为' + length + '，剩余字数：' + (max - length);
    $("#summaryHelp").html(message);
    $("#summaryResult").html("");
}

function genSummary(){
    let content = $("#summariedContent").val();
    let prompt = $("#prompt").val();
    $("#genSummaryBtn").attr("disabled","disabled");
    $.post("/completion/summary",{"content":content,"prompt":prompt},function (d) {
        console.log(d);
        $("#genSummaryBtn").removeAttr("disabled");
        if (d.code == '200'){
            $("#summaryResult").text(d.data);
        }
    },"json");
}