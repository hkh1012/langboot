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
var sid = '';
source.onmessage = function (event) {
    if (event.data == '[END]'){
        if (answerContent.indexOf('```') >=0 ){
            answerContent = fixMarkdown(answerContent);
        }
        $("#sendBtn").prop("disabled", false);
    }else {
        answerContent += event.data;
    }
    fillLeftChatContent(answerContent);
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
    answerContent = "";
    let content = $("#sayContent").val();
    if (content.trim() == ""){
        return false;
    }
    $("#sendBtn").prop("disabled", true);
    conversation.scrollTop = conversation.scrollHeight;
    fillRightChatContent(content);
    initLeftChatContent();
    lastChild = $(resultDiv).children().last().children().last();
    $("#sayContent").val("");
    let useLk = !localStorage.getItem("useLk") ? false : localStorage.getItem("useLk");
    let useHistory = !localStorage.getItem("useHistory") ? false : localStorage.getItem("useHistory");
    $.post("/sse/send",{"sessionId":sessionId,"content":content,"sid":sid,"kid":kid,"useLk":useLk,"useHistory":useHistory})
        .done(function (d) {
        console.log(d);
    }).always(function() {
        // 无论请求成功或失败都会被执行的操作
        $("#sendBtn").prop("disabled", false);
    });
}

function fillRightChatContent(message) {
    $(resultDiv).append("<div class='text-right '><div class='human-question'>" + message + "</div></div>");
}

function initLeftChatContent() {
    $(resultDiv).append("<div class='text-left'><div class='gpt-answer'></div></div>");
}

function fillLeftChatContent(message) {
    lastChild.html(message);
    conversation.scrollTop = conversation.scrollHeight;
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
}

function useLocalKnowledge(o){
    let useLk = localStorage.getItem("useLk");
    if (useLk == null){
        useLk = 'false';
    }
    if (useLk == "false"){
        localStorage.setItem("useLk","true");
        $(o).addClass("selected")
    }else {
        localStorage.setItem("useLk","false");
        $(o).removeClass("selected")
    }
}

function useHistory(o){
    let useHistory = localStorage.getItem("useHistory");
    if (useHistory == null){
        useHistory = 'false';
    }
    if (useHistory == "false"){
        localStorage.setItem("useHistory","true");
        $(o).addClass("selected")
    }else {
        localStorage.setItem("useHistory","false");
        $(o).removeClass("selected")
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
    $.ajax({
        url: '/chatSession/remove',
        type: 'POST',
        data: JSON.stringify({"sid":sid}),
        dataType: 'json',
        contentType: 'application/json',
        success: function(data) {
            $(resultDiv).html('');
            sid = '';
            $(o).parent().parent().remove();
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


function showKnowledgeForm(){
    $("#knowledgeName").addClass("h");
}


function showPreviewModal(idx) {
    let modal = $('#previewModal');
    let knowledge = localStorage.getItem("knowledge");
    let parseKn = JSON.parse(knowledge);
    let content = parseKn.attachList[idx].content;
    $(modal).find('#previewContent').html(content);
    $(modal).modal({
        keyboard: false
    })
}



function loadLocalStatus(){
    let useLk = localStorage.getItem("useLk");
    if (useLk == null){
        useLk = 'false';
    }
    if (useLk == "false"){
        $(".iconKnowledge").removeClass("selected");
        $(".iconKnowledge2").removeClass("selected");
    }else {
        $(".iconKnowledge").addClass("selected")
        $(".iconKnowledge2").addClass("selected")
    }
    let useHistory = localStorage.getItem("useHistory");
    if (useHistory == null){
        useHistory = 'false';
    }
    if (useHistory == "false"){
        $(".iconHistory").removeClass("selected")
    }else {
        $(".iconHistory").addClass("selected")
    }
    let localSid = localStorage.getItem("sid");
    let o = null;
    if (localSid == null || localSid == undefined || localSid == 'undefined'){
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
    loadSession();
});