function contentChange(){
    let message = '';
    let content = $("#agentContent").val();
    let length = content ? content.length : 0;
    let max = 3000;
    message = '当前文本长度为' + length + '，剩余字数：' + (max - length);
    $("#agentHelp").html(message);
    $("#agentResult").html("");
}

function agentCheck(){
    let content = $("#agentContent").val();
    $("#agentBtn").attr("disabled","disabled");
    $.post("/agent/demand/propose",{"content":content,"fid":'51-af7dnc8pmj2b1r9uccg529le2deil69jb3o0f2ho73l8o9h'},function (d) {
        console.log(d);
        $("#agentBtn").removeAttr("disabled");
        if (d.code == '200'){
            let data = d.data;
            $("#agentResult").html(data);
        }
    },"json");
}