$.ajaxSetup({
    error: function (jqXHR, textStatus, errorThrown) {
        switch (jqXHR.status) {
            case (500):
                if (jqXHR.responseJSON.message == '会话过期'){
                    window.location.href = '/login';
                }
                break;
            case (401):
                //TODO 未登录
                window.location.href = '/login';
                break;
            case (403):
                //TODO 无权限执行此操作
                break;
            case (408):
                //TODO 请求超时
                break;
            default:
                //TODO 未知错误
        }

    }
});

var sessionId = new Date().getTime() + "-" + Math.random();
var resultDiv = document.getElementById('chatList');
var conversation = document.getElementById('conversation');
var answerContent = "";
var source = new EventSource('/sse/subscribe?sessionId=' + sessionId);
var lastChild = '';
var kid = '';
var docId = '';
var sid = '';
var audioArr = [];
source.onmessage = function (event) {
    if (event.data.startsWith('[fst-audio]') || event.data.startsWith('[all-audio]')){
        let httpUrl = event.data.substring(11);
        let mediaId = httpUrl.substring(httpUrl.lastIndexOf('/')+1).replace('.mp3','');
        let lastFlag = false;
        if (event.data.startsWith('[all-audio]')){
            lastFlag = true;
        }
        let audioData = {"mediaId":mediaId,"httpUrl":httpUrl,"lastFlag":lastFlag};
        audioArr.push(audioData);
        let multiple = event.data.startsWith('[fst-audio]');
        fillLeftChatContentAudio(multiple);
    }else if (event.data.startsWith('[sub-audio]') || event.data.startsWith('[end-audio]')){
        let httpUrl = event.data.substring(11);
        let mediaId = httpUrl.substring(httpUrl.lastIndexOf('/')+1).replace('.mp3','');
        let lastFlag = false;
        if (event.data.startsWith('[end-audio]')){
            lastFlag = true;
        }
        let audioData = {"mediaId":mediaId,"httpUrl":httpUrl,"lastFlag":lastFlag};
        audioArr.push(audioData);
    }else {
        if (event.data == '[END]'){
            if (answerContent.indexOf('```') >=0 ){
                answerContent = fixMarkdown(answerContent);
            }
            $("#sendBtn").prop("disabled", false);
        }else {
            answerContent += event.data;
        }
        fillLeftChatContent(answerContent);
    }

};

source.onopen = function (event) {
    console.log("sse opened ...");
};


source.onerror = function (event){
    $("#sendBtn").prop("disabled", false);
    console.log("sse error ...",event);
}

conversation.addEventListener('scroll', function() {
    if (conversation.scrollTop + conversation.clientHeight >= conversation.scrollHeight) {
        // 滚动到底部
        conversation.scrollTop = conversation.scrollHeight;
    }
});

function isLineBreak(str) {
    // var regExp = /^[\r\n]+$/;
    // return regExp.test(str);
    var b = false;
    if (str=='\ufeff' || str == '\u202a' || str == '\u0000'
        || str == '\u3164' || str == '\u2800' || str == '\\n' || str == '\\r' || str == ''
        || str.indexOf('\\n')>=0 || /^[\r\n]+$/.test(str) || /\n/.test(str) || /\r/.test(str)){
        b = true;
    }
    return b;
}

function sendContent() {
    let sending = $("#sendBtn").prop("disabled");
    if (sending){
        alertMsg("消息已经发送，请稍后",true);
        return ;
    }
    answerContent = "";
    let content = $("#sayContent").val();
    if (content.trim() == ""){
        return false;
    }
    $("#sendBtn").prop("disabled", true);
    $('#nearest-content').html('');
    conversation.scrollTop = conversation.scrollHeight;
    fillRightChatContent(content);
    initLeftChatContent();
    lastChild = $(resultDiv).children().last().children().first();
    $("#sayContent").val("");
    let useLk = !localStorage.getItem("useLk") ? false : localStorage.getItem("useLk");
    let useHistory = !localStorage.getItem("useHistory") ? false : localStorage.getItem("useHistory");
    let useDocMode = !localStorage.getItem("useDocMode") ? false : localStorage.getItem("useDocMode");
    let useNetMode = !localStorage.getItem("useNetMode") ? false : localStorage.getItem("useNetMode");
    $.post("/sse/send",{"sessionId":sessionId,"content":content,"sid":sid,"kid":kid,"useLk":useLk,"useDocMode":useDocMode,"useNetMode":useNetMode,"docId":docId,"useHistory":useHistory})
        .done(function (d) {
            let _html = '';
            for (let i = 0; i < d.data.length; i++) {
                let chunk = d.data[i];
                _html += '<div class="nearest-chunk">' + chunk + '</div>';
            }
            $('#nearest-content').html(_html);
    }).always(function() {
        // 无论请求成功或失败都会被执行的操作
        $("#sendBtn").prop("disabled", false);
    });
}
function fillRightChatContent(message) {
    $(resultDiv).append("<div class='text-right '><div class='human-question'>" + message + "</div></div>");
}

function fillRightChatContentAudio(mediaFile) {
    let lastQuestion = $(".human-question").last();
    lastQuestion.append("<div class='human-question-audio' id='" + mediaFile.mfid + "'></div>");
    conversation.scrollTop = conversation.scrollHeight;
    var audio=document.createElement("audio");
    audio.controls=true;
    document.getElementById(mediaFile.mfid).appendChild(audio);
    //简单利用URL生成播放地址，注意不用了时需要revokeObjectURL，否则霸占内存
    audio.src=mediaFile.httpUrl;
    audio.pause();

    setTimeout(function(){
        (window.URL||webkitURL).revokeObjectURL(audio.src);
    },5000);
}

function initLeftChatContent() {
    $(resultDiv).append("<div class='text-left'><div class='gpt-answer'></div></div>");
}

function fillLeftChatContent(message) {
    lastChild.html(message);
    conversation.scrollTop = conversation.scrollHeight;
}

function fillLeftChatContentAudio(multiple) {
    let audioData = audioArr.shift();
    // lastChild.append("<div class='gpt-answer-audio' id='" + audioData.mediaId +"'></div>");
    $("<div class='gpt-answer-audio' id='" + audioData.mediaId +"'></div>").insertAfter(lastChild);
    conversation.scrollTop = conversation.scrollHeight;
    var audio=document.createElement("audio");
    audio.controls=true;
    document.getElementById(audioData.mediaId).appendChild(audio);
    //简单利用URL生成播放地址，注意不用了时需要revokeObjectURL，否则霸占内存
    audio.src=audioData.httpUrl;

    audio.addEventListener('ended', playEndedHandler, false);
    audio.play();

    function playEndedHandler() {
        if (multiple && audioArr.length>0){
            let audioItem = audioArr.shift();
            audio.src = audioItem.httpUrl;
            audio.play();
            audioItem.lastFlag && audio.removeEventListener('ended',playEndedHandler,false);
        }
    }

    setTimeout(function(){
        (window.URL||webkitURL).revokeObjectURL(audio.src);
    },5000);

}

function fillLeftChatContentImage(mediaFileList){
    for (let i = 0; i < mediaFileList.length; i++) {
        $("<div class='gpt-answer-image' id='" + mediaFileList[i].mfid +"'><img src='" + mediaFileList[i].httpUrl +"' class='visionSelectedImage'></div>").insertAfter(lastChild);
    }

}

function fixMarkdown(message) {
    message = message.replaceAll('<br>','\n');
    message = message.replaceAll('&nbsp;',' ');
    // message = message.replaceAll('&lt;','<');
    // message = message.replaceAll('&gt;','>');
    let converter = new showdown.Converter();
    converter.setOption('tables', 'true');
    message = converter.makeHtml(message);
    return message;
}

function removeConversation(o){
    showConfirm("确定要清空该会话历史记录吗？",function (){
        $.ajax({
            url: '/conversation/remove',
            type: 'POST',
            data: JSON.stringify({"sid":sid}),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                $("#chatList").html('');
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
    } ,null);
}

function useLocalKnowledge(){
    let useLk = localStorage.getItem("useLk");
    if (useLk == null){
        useLk = 'false';
    }
    if (useLk == "false"){
        alertMsg("已启用本地知识库",true);
        localStorage.setItem("useLk","true");
        $("#setting-item-local-result").html("是");

        localStorage.setItem("useDocMode","false");
        $("#setting-item-doc-result").html("否");
        localStorage.setItem("useNetMode","false");
        $("#setting-item-net-result").html("否");

        $("#setting-item-knowledge-result").removeClass("h")
        $("#setting-item-file-upload").addClass("h");
    }else {
        alertMsg("未启用知识库",true);
        localStorage.setItem("useLk","false");
        $("#setting-item-local-result").html("否");
        $("#setting-item-knowledge-result").addClass("h")
        $("#setting-item-file-upload").addClass("h");
    }
}

function useDocMode(){
    let useDocMode = localStorage.getItem("useDocMode");
    if (useDocMode == null){
        useDocMode = 'false';
    }
    if (useDocMode == "false"){
        alertMsg("已使用文档模式",true);
        localStorage.setItem("useDocMode","true");
        $("#setting-item-doc-result").html("是");

        localStorage.setItem("useLk","false");
        $("#setting-item-local-result").html("否");
        localStorage.setItem("useNetMode","false");
        $("#setting-item-net-result").html("否");

        $("#setting-item-file-upload").removeClass("h");
        $("#setting-item-knowledge-result").addClass("h")
    }else {
        alertMsg("未启用文档模式",true);
        localStorage.setItem("useDocMode","false");
        $("#setting-item-doc-result").html("否");
        $("#setting-item-file-upload").addClass("h");
        $("#setting-item-knowledge-result").addClass("h")
    }
}

function uploadDoc(){
    if (typeof window.FileReader !== 'function') {
        console.log("抱歉，您的浏览器不支持读取文件！");
        return;
    }

    let input = document.createElement('input');
    input.type = 'file';

    input.onchange = function(event) {
        var file = event.target.files[0];
        let formData = new FormData();
        formData.append('file', file);
        $.ajax({
            url: '/media/upload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                let media = response.data;
                docId = media.mfid;
                localStorage.setItem("doc",JSON.stringify(media))
                $("#setting-item-file-upload").html(media.fileName);
                $("#setting-item-file-upload").attr("docId",media.mfid);
            },
            error: function(xhr, status, error) {
                // 处理错误
                console.error(error);
            }
        });
    };
    input.click();
}

function useNetMode(){
    let useNetMode = localStorage.getItem("useNetMode");
    if (useNetMode == null){
        useNetMode = 'false';
    }
    if (useNetMode == "false"){
        alertMsg("已启用联网模式",true);
        localStorage.setItem("useNetMode","true");
        $("#setting-item-net-result").html("是");

        localStorage.setItem("useLk","false");
        $("#setting-item-local-result").html("否");
        localStorage.setItem("useDocMode","false");
        $("#setting-item-doc-result").html("否");

        $("#setting-item-knowledge-result").addClass("h")
        $("#setting-item-file-upload").addClass("h");

    }else {
        alertMsg("未启用联网模式",true);
        localStorage.setItem("useNetMode","false");
        $("#setting-item-net-result").html("否");

        $("#setting-item-knowledge-result").addClass("h")
        $("#setting-item-file-upload").addClass("h");
    }
}

function useHistory(){
    let useHistory = localStorage.getItem("useHistory");
    if (useHistory == null){
        useHistory = 'false';
    }
    if (useHistory == "false"){
        alertMsg("已携带历史对话",true);
        localStorage.setItem("useHistory","true");
        $("#setting-item-history-result").html("是");
    }else {
        alertMsg("未携带历史对话",true);
        localStorage.setItem("useHistory","false");
        $("#setting-item-history-result").html("否");
    }
}

/**
 * 加载会话
 */
function loadSession() {
    $("#left-top").find(".session-item").remove();
    $.get("/chatSession/list",{},function (d) {
        if (d.code == '200'){
            if (d.data != null && d.data.length > 0) {
                for (let i = 0; i < d.data.length; i++) {
                    let item = d.data[i];
                    $("#left-top").append('<div ondblclick="setSession(this);" onclick="selectSession(this);" class="session-item" sid="' + item.sid +'"><span class="session-item-title">' + item.title +'</span><span class="session-item-operate h"><span class="session-item-save" onclick="saveSessionTitle(this);">保存</span><span class="session-item-remove" onclick="removeSession(this);">删除</span></span></div>');
                }
                loadLocalStatus();
            }else {
                addSession();
            }
        }
    },"json");
}

/**
 * 选择会话
 * @param o
 */
function selectSession(o){
    $(".session-item").removeClass("selected-session");
    $(".session-item-operate").addClass("h");
    $(".session-item-save").addClass("h");
    $(".session-item-remove").addClass("h");
    $(".session-item-title").removeClass("session-item-title-selected").attr("contenteditable",false);
    sid = $(o).attr("sid");
    localStorage.setItem("sid",sid);
    $(o).addClass('selected-session');
    let title  = $($(o).children()[0]).html();
    $("#top-session-title").html(title);
    loadConversation(sid);
}
/**
 * 设置会话
 * @param o
 */
function setSession(o){
    selectSession(o);
    $(o).find(".session-item-title").addClass("session-item-title-selected").attr("contenteditable",true);
    $(".session-item-title-selected").focus();
    $(o).find(".session-item-operate").removeClass("h");
    $(o).find(".session-item-save").removeClass("h");
    $(o).find(".session-item-remove").removeClass("h");
}

/**
 * 添加会话
 */
function addSession() {
    let sessionItems = $("#left-top").find(".session-item");
    if (sessionItems != null && sessionItems.length >= 10) {
        alertMsg("会话数不能超过10个",true);
        return;
    }
    $.ajax({
        url: '/chatSession/save',
        type: 'POST',
        data: JSON.stringify({"title":"","modelId":0,"sid":""}),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            loadSession();
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}

function saveSessionTitle(o){
    let title = $(o).parent().parent().find(".session-item-title").text();
    $.ajax({
        url: '/chatSession/save',
        type: 'POST',
        data: JSON.stringify({"title":title,"modelId":0,"sid":sid}),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            selectSession($(o).parent().parent());
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}
function removeSession(o){
    event.preventDefault();
    event.stopPropagation();
    event.cancelBubble = true;
    let _sid = $(o).parent().parent().attr("sid");
    $.ajax({
        url: '/chatSession/remove',
        type: 'POST',
        data: JSON.stringify({"sid":_sid}),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            // sid = '';
            $(o).parent().parent().remove();
            if (_sid == sid){
                sid = '';
                localStorage.setItem("sid",sid);
                $(resultDiv).html('');
                loadSession();
            }

        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}
function loadConversation(sid){
    $(resultDiv).html('');
    $.get("/conversation/list/" + sid,{},function (d) {
        if (d.code == '200'){
            if (d.data != null && d.data.length > 0) {
                for (let i = 0; i < d.data.length; i++) {
                    let item = d.data[i];
                    let content = item.content;
                    if (item.type=='Q'){
                        $(resultDiv).append("<div class='text-right '><div class='human-question'>" + content + "</div></div>");
                    }else {
                        if (item.content.indexOf('```') >=0 || item.content.indexOf('<br>') >=0 || item.content.indexOf('&nbsp;') >=0){
                            content = fixMarkdown(item.content);
                        }
                        $(resultDiv).append("<div class='text-left'><div class='gpt-answer'>" + content +"</div></div>");
                    }
                }
                conversation.scrollTop = conversation.scrollHeight;
            }
        }
    },"json");
}


function loadLocalStatus(){
    let useLk = localStorage.getItem("useLk");
    if (useLk == null){
        useLk = 'false';
    }
    if (useLk == "false"){
        $("#setting-item-local-result").html("否");
    }else {
        $("#setting-item-local-result").html("是");
        $("#setting-item-knowledge-result").removeClass("h");
    }

    let useDocMode = localStorage.getItem("useDocMode");
    if (useDocMode == null){
        useDocMode = 'false';
    }
    if (useDocMode == "false"){
        $("#setting-item-doc-result").html("否");
    }else {
        $("#setting-item-doc-result").html("是");
        $("#setting-item-file-upload").removeClass("h");
    }

    let useNetMode = localStorage.getItem("useNetMode");
    if (useNetMode == null){
        useNetMode = 'false';
    }
    if (useNetMode == "false"){
        $("#setting-item-net-result").html("否");
    }else {
        $("#setting-item-net-result").html("是");
    }

    let useHistory = localStorage.getItem("useHistory");
    if (useHistory == null){
        useHistory = 'false';
    }
    if (useHistory == "false"){
        $("#setting-item-history-result").html("否");
    }else {
        $("#setting-item-history-result").html("是");
    }
    let localSid = localStorage.getItem("sid");
    let o = null;
    if (localSid == null || localSid == undefined || localSid == 'undefined' || localSid == ''){
        o = $("#left-top").find(".session-item")[0];
    }else {
        o = $("#left-top").find("div[sid='"+localSid+"']")[0];
    }

    selectSession(o);
}

function showSessionList(){
    $("#full-screen-bg").removeClass("h");
    $("#left-content").addClass("left-menu-show");
}

function hideSessionList(){
    $("#full-screen-bg").addClass("h");
    $("#left-content").removeClass("left-menu-show");
    $("#right-content").removeClass("right-content-show");
}

function showRightContent(){
    $("#full-screen-bg").removeClass("h");
    $("#right-content").addClass("right-content-show");
}

function openKnowledgeSelectDiv(){
    $(".knowledge-select-container").removeClass("h");
    $("#knowledge-all").html('');
    $('#knowledge-select-warning-msg').html('');
    $.get("/knowledge/all",{},function (d) {
        if (d.code == '200'){
            if (d.data != null && d.data.length > 0) {
                let _html = '';
                let knowledgeStr = localStorage.getItem("knowledge");
                let knowledge = null;
                if (knowledgeStr){
                    knowledge = JSON.parse(knowledgeStr);
                }
                for (let i = 0; i < d.data.length; i++) {
                    let item = d.data[i];
                    let checked = '';
                    if (knowledge && item.kid == knowledge.kid) {
                        checked = 'checked';
                    }
                    if (item.kid ){
                        _html +='<div class="radio" style="margin: 20px"><label><input type="radio" name="knowledgeRadio" id="knowledgeRadio' +item.kid+'" value="' + item.kid+'" style="margin-right: 5px;" ' + checked +'>' + item.kname+'</label></div>';
                    }
                }
                $("#knowledge-all").html(_html);
            }
        }
    },"json");
}

function selectKnowledge(){
    let selectedKid = $('input[name="knowledgeRadio"]:checked').val();
    if (!selectedKid){
        $('#knowledge-select-warning-msg').html('请选择知识库!');
        return;
    }
    kid = selectedKid;
    let kname = $('input[name="knowledgeRadio"]:checked').parent().text();
    $("#setting-item-knowledge-result").html(kname);
    let knowledge = {"kid":kid,"kname":kname};
    localStorage.setItem("knowledge",JSON.stringify(knowledge));
    $(".knowledge-select-container").addClass("h");
}

function loadSelectedKnowledge(){
    let knowledgeStr = localStorage.getItem("knowledge");
    let knowledge = null;
    if (knowledgeStr){
        knowledge = JSON.parse(knowledgeStr);
        kid = knowledge.kid;
        $("#setting-item-knowledge-result").html(knowledge.kname);
    }
}

function loadLocalDoc(){
    let docStr = localStorage.getItem("doc");
    let doc = null;
    if (docStr){
        doc = JSON.parse(docStr);
        docId = doc.mfid;
        $("#setting-item-file-upload").attr("docId",docId);
        $("#setting-item-file-upload").html(doc.fileName);
    }
}

function closeFormDiv(){
    $(".knowledge-select-container").addClass("h");
}

var recordStatus = {
    started : false,
    recording : false,
    pausing : false,
    playing : false
}

function closeVoiceDiv(){
    $("#chatOpeDiv").removeClass("h");
    $("#voiceDiv").addClass("h");
    $("#conversationParent").removeClass("bottom250");
    conversation.scrollTop = conversation.scrollHeight;
    voiceCancel();
}

function triggerToVoice(){
    console.log('切换到语音模式');
    $("#chatOpeDiv").addClass("h");
    $("#voiceDiv").removeClass("h");
    $("#conversationParent").addClass("bottom250");
    conversation.scrollTop = conversation.scrollHeight;
    recReq();
}

function voiceStartRecord(){
    recStart();
    recordStatus.started = true;
    recordStatus.recording = true;
    $("#voiceRecordBtn").attr("disabled","disabled");
    $("#voicePauseBtn").removeAttr("disabled");
    $("#voiceStopBtn").removeAttr("disabled");
    $("#voicePlayBtn").attr("disabled","disabled");
    $("#voiceCancelBtn").removeAttr("disabled");
    $("#voiceMidTitleDiv").html("我在听，请说话");
}

function voicePauseRecord(){
    if (!recordStatus.pausing){
        recPause();
        $("#voicePauseBtn").html("续录");
    }else {
        recResume();
        $("#voicePauseBtn").html("暂停");
    }
    recordStatus.pausing = !recordStatus.pausing;
}

function voiceStopRecord(){
    recordStatus.started = false;
    recordStatus.recording = false;
    // $("#voiceRecordBtn").removeAttr("disabled");
    $("#voicePauseBtn").attr("disabled","disabled");
    $("#voiceStopBtn").attr("disabled","disabled");
    $("#voicePlayBtn").removeAttr("disabled");
    $("#voiceSubmitBtn").removeAttr("disabled");
    $("#voiceMidTitleDiv").html("点击按钮，提交录音");
    recStop();
}

function voicePlayRecord(){
    if (!recordStatus.playing){
        recPlay();
        $("#voicePlayBtn").html("暂停");
    }else {
        $("#" + audioDivId).remove();
        $("#voicePlayBtn").html("播放");
    }
    recordStatus.playing = !recordStatus.playing;
}

function voiceCancel(){
    initRecordStatus();
    initRecordBtns();
    $("#voiceMidTitleDiv").html("点击按钮，开始录音");
    recStop();
    wave = null;
    recBlob = null;
    audio = null;
}

function initRecordStatus(){
    recordStatus.playing = false;
    recordStatus.started = false;
    recordStatus.pausing = false;
    recordStatus.recording = false;
}

function initRecordBtns(){
    $("#voiceRecordBtn").removeAttr("disabled");
    $("#voicePauseBtn").attr("disabled","disabled");
    $("#voiceStopBtn").attr("disabled","disabled");
    $("#voicePlayBtn").attr("disabled","disabled");
    $("#voiceCancelBtn").attr("disabled","disabled");
    $("#voiceSubmitBtn").attr("disabled","disabled");
}

function voiceSubmitRecord(){
    initRecordBtns();
    initRecordStatus();
    recUpload();
    $("#voiceMidTitleDiv").html("点击按钮，开始录音");
}


var wave,recBlob,audio;
/**调用RequestPermission打开录音请求好录音权限  Call RequestPermission to open the recording and request the recording permission.**/
function recReq(){//一般在显示出录音按钮或相关的录音界面时进行此方法调用，后面用户点击开始录音时就能畅通无阻了
    console.log('开始请求授权...');
    RecordApp.RequestPermission(function(){
        console.log('已授权');
    },function(err,isUserNotAllow){
        console.log('授权失败');
    });
};

/**开始录音  Start recording**/
function recStart(){
    if(!RecordApp.Current){
        console.log('未请求权限');
        return;
    };

    if(RecordApp.Current==RecordApp.Platforms.Native){
        console.log('正在使用Native录音，底层由App原生层提供支持');
    }else{
        console.log('正在使用H5录音，底层由Recorder直接提供支持');
    };

    var set={
        type:"mp3"
        ,bitRate:16
        ,sampleRate:16000
        ,onProcess:function(buffers,powerLevel,bufferDuration,bufferSampleRate,newBufferIdx,asyncEnd){
            //录音实时回调，大约1秒调用12次本回调
            // document.querySelector(".recpowerx").style.width=powerLevel+"%";
            // document.querySelector(".recpowert").innerText=formatMs(bufferDuration,1)+" / "+powerLevel;

            //可视化图形绘制
            wave.input(buffers[buffers.length-1],powerLevel,bufferSampleRate);
        }
    };

    wave=null;
    recBlob=null;
    RecordApp.Start(set,function(){
        console.log('录制中');
        //此处创建这些音频可视化图形绘制浏览器支持妥妥的
        wave=Recorder.WaveView({elem:".wave-container"});
    },function(err){
        console.log('开始录音失败');
    });
};

/**暂停录音  Passing recording**/
function recPause(){
    if(RecordApp.GetCurrentRecOrNull()){
        RecordApp.Pause();
        console.log('已暂停');
    }
};
/**恢复录音  Resume recording**/
function recResume(){
    if(RecordApp.GetCurrentRecOrNull()){
        RecordApp.Resume();
        console.log('继续录音中...');
    }
};

/**结束录音，得到音频文件  Stop recording and get audio files**/
function recStop(){
    if(!RecordApp.Current){
        console.log('未请求权限');
        return;
    };

    RecordApp.Stop(function(aBuf,duration,mime){
        var blob=new Blob([aBuf],{type:mime});
        console.log(blob,(window.URL||webkitURL).createObjectURL(blob),"duration:"+duration+"ms");

        recBlob=blob;
        // reclog(Html_$T("YcB5::已录制mp3：{1}ms {2}字节，可以点击播放、上传、本地下载了",0,formatMs(duration),blob.size),2);

    },function(msg){
        // reclog(Html_$T("nIFF::录音失败：")+msg,1);
    });
};
function recStopX(){
    RecordApp.Stop(
        null //success传null就只会清理资源，不会进行转码
        ,function(msg){
            // reclog(Html_$T("vHlR::已清理，错误信息：")+msg);
        }
    );
};
var audioDivId = '';
/**播放  Play**/
function recPlay(){
    if(!recBlob){
        // reclog(Html_$T("kWpA::请先录音，然后停止后再播放"),1);
        return;
    };
    var cls=("a"+Math.random()).replace(".","");
    audioDivId = cls;
    $("body").append('<span id="' + cls +'" class="'+cls+'" style="display: none;"></span>');
    // reclog(Html_$T('XCKe::播放中: ')+'<span class="'+cls+'"></span>');
    var audio=document.createElement("audio");
    audio.controls=true;
    document.querySelector("."+cls).appendChild(audio);
    //简单利用URL生成播放地址，注意不用了时需要revokeObjectURL，否则霸占内存
    audio.src=(window.URL||webkitURL).createObjectURL(recBlob);
    audio.play();

    setTimeout(function(){
        (window.URL||webkitURL).revokeObjectURL(audio.src);
    },5000);

};

/**上传  Upload**/
function recUpload(){
    var blob=recBlob;
    if(!blob){
        // reclog(Html_$T("SLaX::请先录音，然后停止后再上传"),1);
        return;
    };

    let formData = new FormData();
    formData.append('file', blob,"recorder.mp3");
    $.ajax({
        url: '/media/upload',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            let media = response.data;
            $.ajax({
                url: '/audio/transcribe',
                type: 'POST',
                data: JSON.stringify({"mediaId":media.mfid}),
                dataType: 'json',
                contentType: 'application/json',
                success: function(resp) {
                    sendAudioContent(media,resp.data);
                },
                error: function(xhr, status, error) {
                    console.error(error);
                }
            });

        },
        error: function(xhr, status, error) {
            // 处理错误
            console.error(error);
        }
    });
};

function sendAudioContent(media,content) {
    answerContent = "";
    if (content.trim() == ""){
        return false;
    }
    $('#nearest-content').html('');
    conversation.scrollTop = conversation.scrollHeight;
    fillRightChatContent(content);
    fillRightChatContentAudio(media);
    initLeftChatContent();
    lastChild = $(resultDiv).children().last().children().first();
    let useLk = !localStorage.getItem("useLk") ? false : localStorage.getItem("useLk");
    let useHistory = !localStorage.getItem("useHistory") ? false : localStorage.getItem("useHistory");

    $.ajax({
        url: '/sse/audioChat',
        type: 'POST',
        data: JSON.stringify({"sessionId":sessionId,"content":content,"sid":sid,"kid":kid,"useLk":useLk,"useHistory":useHistory,"mediaId":media.mfid}),
        dataType: 'json',
        contentType: 'application/json',
        success: function(resp) {
            // 相似度文本
            let nearestList = resp.data.nearestList;
            let _html = '';
            for (let i = 0; i < nearestList.length; i++) {
                let chunk = nearestList[i];
                _html += '<div class="nearest-chunk">' + chunk + '</div>';
            }
            $('#nearest-content').html(_html);
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}

/**本地下载  Local download**/
function recLocalDown(){
    if(!recBlob){
        // reclog(Html_$T("hFmL::请先录音，然后停止后再下载"),1);
        return;
    };
    var cls=("a"+Math.random()).replace(".","");
    $("body").append('<span class="'+cls+'"></span>');
    recdown64.lastCls=cls;
    // reclog(Html_$T('UrgF::点击 ')+'<span class="'+cls+'"></span>'+Html_$T('c7b7:: 下载，或复制文本')
    //     +'<button onclick="recdown64(\''+cls+'\')">'+Html_$T('6Bln::生成Base64文本')+'</button><span class="'+cls+'_b64"></span>');

    var fileName="recordapp-"+Date.now()+".mp3";
    var downA=document.createElement("A");
    downA.innerHTML="下载"+fileName;
    downA.href=(window.URL||webkitURL).createObjectURL(recBlob);
    downA.download=fileName;
    document.querySelector("."+cls).appendChild(downA);
    if(/mobile/i.test(navigator.userAgent)){
        alert("因移动端绝大部分国产浏览器未适配Blob Url的下载，所以本demo代码在移动端未调用downA.click()。请尝试点击日志中显示的下载链接下载");
    }else{
        downA.click();
    }

    //不用了时需要revokeObjectURL，否则霸占内存
    //(window.URL||webkitURL).revokeObjectURL(downA.href);
};
function recdown64(cls){
    var el=document.querySelector("."+cls+"_b64");
    if(recdown64.lastCls!=cls){
        el.innerHTML='<span style="color:red">'+"老的数据没有保存，只支持最新的一条"+'</span>';
        return;
    }
    var reader = new FileReader();
    reader.onloadend = function() {
        el.innerHTML='<textarea></textarea>';
        el.querySelector("textarea").value=reader.result;
    };
    reader.readAsDataURL(recBlob);
};

function triggerToVision(){
    console.log('切换到vision模式');
    $("#visionDiv").removeClass("h");
    $("#conversationParent").addClass("bottom400");
    conversation.scrollTop = conversation.scrollHeight;
}

function visionCancel(){
    $("#visionDiv").addClass("h");
    $("#visionMidDiv").find("img").remove(".visionSelectedImage");
    $("#conversationParent").removeClass("bottom400");
    conversation.scrollTop = conversation.scrollHeight;
    uploadImages = [];
}

var uploadImages = [];
function visionAddImage(){
    if (typeof window.FileReader !== 'function') {
        console.log("抱歉，您的浏览器不支持读取文件！");
        return;
    }

    var input = document.createElement('input');
    input.type = 'file';
    input.accept = 'image/*'; // 只接受图像文件

    input.onchange = function(event) {
        var file = event.target.files[0];
        var reader = new FileReader();

        reader.onload = function() {
            // 在这里可以处理所选图像的数据
            let imageData = reader.result;
            let image = document.createElement('img');
            image.src = imageData;
            image.className='visionSelectedImage';
            $(".visionAddBtn").before(image);
            $.ajax({
                url: '/media/base64Upload',
                type: 'POST',
                data: JSON.stringify({"base64Image":imageData}),
                dataType: 'json',
                contentType: 'application/json',
                success: function(resp) {
                    // 相似度文本
                    uploadImages.push(resp.data);
                },
                error: function(xhr, status, error) {
                    console.error(error);
                }
            });
        };
        reader.readAsDataURL(file);
    };
    input.click();
}

function fillRightChatContentImages(images){
    let lastQuestion = $(".human-question").last();
    let _imgHtml = '';
    for (let i = 0; i < images.length; i++) {
        let image = images[i];
        _imgHtml += "<img id='" + image.mfid + "' src='" + image.httpUrl + "' class='visionSelectedImage'>";
    }
    lastQuestion.append("<div class='human-question-images'>" + _imgHtml +"</div>");
    conversation.scrollTop = conversation.scrollHeight;
}

function imageSubmit(){
    var imageMode = $('input[type="radio"]').filter('[name="imageMode"]:checked').val();
    console.log(imageMode);
    if (imageMode == "1"){
        visionSubmit();
    }else {
        createImageSubmit();
    }
}

function visionSubmit(){
    let content = $("#visionTextArea").val();
    if (content == null || content.trim().length == 0){
        alertMsg("请输入文本内容",true);
        return;
    }
    if (uploadImages.length==0){
        alertMsg("请至少上传一张图片",true);
        return;
    }

    $('#nearest-content').html('');
    conversation.scrollTop = conversation.scrollHeight;
    fillRightChatContent(content);
    fillRightChatContentImages(uploadImages);
    initLeftChatContent();
    lastChild = $(resultDiv).children().last().children().first();
    let useLk = !localStorage.getItem("useLk") ? false : localStorage.getItem("useLk");
    let useHistory = !localStorage.getItem("useHistory") ? false : localStorage.getItem("useHistory");

    let mediaIds = [];
    for (let i = 0; i < uploadImages.length; i++) {
        mediaIds.push(uploadImages[i].mfid);
    }
    $.ajax({
        url: '/sse/visionChat',
        type: 'POST',
        data: JSON.stringify({"sessionId":sessionId,"content":content,"sid":sid,"kid":kid,"useLk":useLk,"useHistory":useHistory,"mediaIds":mediaIds}),
        dataType: 'json',
        contentType: 'application/json',
        success: function(resp) {
            fillLeftChatContent(resp.data);
            $("#visionTextArea").val("");
            $("#visionMidDiv").find("img").remove(".visionSelectedImage");
            uploadImages = [];
        },
        error: function(xhr, status, error) {
            $("#visionTextArea").val("");
            $("#visionMidDiv").find("img").remove(".visionSelectedImage");
            uploadImages = [];
            console.error(error);
        }
    });
}

function createImageSubmit(){
    let content = $("#visionTextArea").val();
    if (content == null || content.trim().length == 0){
        alertMsg("请输入文本内容",true);
        return;
    }

    $('#nearest-content').html('');
    conversation.scrollTop = conversation.scrollHeight;
    fillRightChatContent(content);
    if (uploadImages.length > 0){
        fillRightChatContentImages(uploadImages);
    }
    initLeftChatContent();
    lastChild = $(resultDiv).children().last().children().first();
    let useLk = !localStorage.getItem("useLk") ? false : localStorage.getItem("useLk");
    let useHistory = !localStorage.getItem("useHistory") ? false : localStorage.getItem("useHistory");

    let mediaIds = [];
    for (let i = 0; i < uploadImages.length; i++) {
        mediaIds.push(uploadImages[i].mfid);
    }
    $.ajax({
        url: '/sse/createImageChat',
        type: 'POST',
        data: JSON.stringify({"sessionId":sessionId,"content":content,"sid":sid,"kid":kid,"useLk":useLk,"useHistory":useHistory,"mediaIds":mediaIds}),
        dataType: 'json',
        contentType: 'application/json',
        success: function(resp) {
            let data = resp.data;
            fillLeftChatContent(data.textMsg);
            fillLeftChatContentImage(data.mediaFileList);
            $("#visionTextArea").val("");
            $("#visionMidDiv").find("img").remove(".visionSelectedImage");
            uploadImages = [];
            conversation.scrollTop = conversation.scrollHeight;
        },
        error: function(xhr, status, error) {
            $("#visionTextArea").val("");
            $("#visionMidDiv").find("img").remove(".visionSelectedImage");
            uploadImages = [];
            console.error(error);
        }
    });
}

function closePreviewDiv(){
    $("#previewImageDIv").addClass("h");
}

function rightPanelTriggle(){
    let text = $("#right-content-header-title").text().trim();
    console.log(text);
    if (text=="相关知识片段"){
        $("#right-content-header-title").text("设置");
        $(".nearest-div").addClass("h");
        $(".settings-div").removeClass("h");
    }else if (text=="设置"){
        $("#right-content-header-title").text("相关知识片段");
        $(".nearest-div").removeClass("h");
        $(".settings-div").addClass("h");
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#sendBtn").click(function() {
        sendContent();
    });
    $("#sayContent").keydown(function(event) {
        if (event.which === 13) {
            event.preventDefault(); // 阻止回车键的默认行为（表单提交）
            // 在这里执行你想要的操作
            sendContent();
        }
    });
    $(".add-session-btn").click(function() {
        addSession();
    });
    $("div").on("click",".visionSelectedImage",function (){
        let attr = $(this).attr("src");
        $("#visionSelectedPreviewImage").attr("src",attr);
        $("#previewImageDIv").removeClass("h");
    });
    loadSelectedKnowledge();
    loadLocalDoc();
    loadSession();
});