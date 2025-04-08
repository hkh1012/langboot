<!DOCTYPE html>
<html>
<head>
    <title>聊天</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="applicable-device" content="pc,mobile">
    <link href="/webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/chat.css" rel="stylesheet">
    <link href="/static/css/bootstrap-icons.css" rel="stylesheet" >
    <script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<#--    <script src="/webjars/sockjs-client/1.0.2/sockjs.min.js"></script>-->
<#--    <script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>-->
    <script src="/static/js/showdown.min.js"></script>
    <script src="/static/js/common/confirm.js"></script>
    <script src="/static/js/recorder/recorder.mp3.min.js"></script>
<#--    <script src="/static/js/recorder/recorder.wav.min.js"></script>-->
    <script src="/static/js/recorder/app.js"></script>

    <script src="/static/js/recorder/engine/wav.js"></script>
    <script src="/static/js/recorder/extensions/waveview.js"></script>
    <script src="/static/js/recorder/extensions/wavesurfer.view.js"></script>
    <script src="/static/js/common/vconsole.min.js"></script>
    <script>
        // var vConsole = new window.VConsole();
    </script>
</head>
<body>
<noscript><h2 style="color: #ff0000">Seems your browser doesn't support Javascript! Websocket relies on Javascript being
        enabled. Please enable
        Javascript and reload this page!</h2></noscript>
<div class="header-fix-btn">
    <a title="显示会话列表" class="iconA showSessionList" href="javascript:void(0);"  onclick="showSessionList(this);">
        <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-justify" viewBox="0 0 16 16">
            <path fill-rule="evenodd" d="M2 12.5a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>
        </svg>
    </a>

    <div class="session-title">
        <span id="top-session-title">New Chat</span>
    </div>

    <a title="设置更多" class="iconA iconKnowledgeSet" href="javascript:void(0);" onclick="showRightContent(this);">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" viewBox="0 0 448 512">
            <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
            <path d="M8 256a56 56 0 1 1 112 0A56 56 0 1 1 8 256zm160 0a56 56 0 1 1 112 0 56 56 0 1 1 -112 0zm216-56a56 56 0 1 1 0 112 56 56 0 1 1 0-112z"/>
        </svg>
<#--        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">-->
<#--            <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>-->
<#--            <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>-->
<#--        </svg>-->
    </a>

</div>
<div id="layout-content" class="shadow-md">
    <div id="left-content" class="container1">
        <div id="left-top">
            <div class="add-session-btn"><span>新建聊天</span></div>
        </div>
        <div id="left-bottom">
            <div id="username-text">
                <div class="row header-bar">
                    <div>
                        <form class="logout-form">
                            <div style="margin-left: 10px;">
                                <label for="name">${sysUser.nickName},您好！</label>
                            </div>
                            <a href="/index">返回</a>
                        </form>
                    </div>
                </div>
            </div>
            <div id="setting-btn">
<#--                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-gear" viewBox="0 0 16 16">-->
<#--                    <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z"/>-->
<#--                    <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z"/>-->
<#--                </svg>-->
            </div>
        </div>
    </div>

    <div id="main-content" class="container">

        <div class="row" style="height: 100%;">
            <div class="col-md-12" id="conversationParent" style="height: 100%;overflow: hidden;padding-left: 0px;padding-right: 0px;">
                <div id="conversation" class="table table-striped">
                    <div id="chatList">
                    </div>
                </div>
            </div>
        </div>
        <div class="row bottom-fix-btn" id="chatOpeDiv" style="position: fixed;height: 50px;background-color: #fff;left: 50%;bottom: 10px;transform: translateX(-50%);width: inherit;margin-right: 0px;margin-left: 0px;display: flex;">
            <div class="col-md-12 chat-input">
                <div class="col-md-11 chat-input-mid" style="margin-left: 0px;margin-right: 0px;display: flex;justify-content: flex-end;align-items: center;padding-left: 0px;padding-right: 10px;">
                    <input type="text" id="sayContent" class="form-control" placeholder="来说点什么吧...">
                    <svg onclick="triggerToVision()" class="vision-btn" xmlns="http://www.w3.org/2000/svg" height="16" width="16" viewBox="0 0 512 512" fill="currentColor">
                        <path d="M448 80c8.8 0 16 7.2 16 16V415.8l-5-6.5-136-176c-4.5-5.9-11.6-9.3-19-9.3s-14.4 3.4-19 9.3L202 340.7l-30.5-42.7C167 291.7 159.8 288 152 288s-15 3.7-19.5 10.1l-80 112L48 416.3l0-.3V96c0-8.8 7.2-16 16-16H448zM64 32C28.7 32 0 60.7 0 96V416c0 35.3 28.7 64 64 64H448c35.3 0 64-28.7 64-64V96c0-35.3-28.7-64-64-64H64zm80 192a48 48 0 1 0 0-96 48 48 0 1 0 0 96z"/>
                    </svg>
                        <svg onclick="triggerToVoice();" class="voice-btn" xmlns="http://www.w3.org/2000/svg" height="16" width="12" viewBox="0 0 384 512" fill="currentColor">
                            <path d="M192 0C139 0 96 43 96 96V256c0 53 43 96 96 96s96-43 96-96V96c0-53-43-96-96-96zM64 216c0-13.3-10.7-24-24-24s-24 10.7-24 24v40c0 89.1 66.2 162.7 152 174.4V464H120c-13.3 0-24 10.7-24 24s10.7 24 24 24h72 72c13.3 0 24-10.7 24-24s-10.7-24-24-24H216V430.4c85.8-11.7 152-85.3 152-174.4V216c0-13.3-10.7-24-24-24s-24 10.7-24 24v40c0 70.7-57.3 128-128 128s-128-57.3-128-128V216z"/>
                        </svg>
                </div>
                <div class="col-md-1" style="text-align: center;padding-left: 0px;padding-right: 0px;">
                    <button id="sendBtn" class="btn btn-success" data-loading-text="发送中..." style="width: 100%;margin-left: 0px;" type="submit">发 送</button>
                </div>
            </div>
        </div>

        <!-- 语音voice面板 -->
        <div class="row bottom-fix-btn h" id="voiceDiv" style="position: fixed;background-color: #fff;left: 50%;bottom: 10px;transform: translateX(-50%);width: inherit;margin-right: 0px;margin-left: 0px;">
            <div class="voiceTopDiv">
                <svg class="voice-close-btn" xmlns="http://www.w3.org/2000/svg" height="20" width="20" viewBox="0 0 512 512" fill="currentColor" onclick="closeVoiceDiv();">
                    <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                    <path d="M256 48a208 208 0 1 1 0 416 208 208 0 1 1 0-416zm0 464A256 256 0 1 0 256 0a256 256 0 1 0 0 512zM175 175c-9.4 9.4-9.4 24.6 0 33.9l47 47-47 47c-9.4 9.4-9.4 24.6 0 33.9s24.6 9.4 33.9 0l47-47 47 47c9.4 9.4 24.6 9.4 33.9 0s9.4-24.6 0-33.9l-47-47 47-47c9.4-9.4 9.4-24.6 0-33.9s-24.6-9.4-33.9 0l-47 47-47-47c-9.4-9.4-24.6-9.4-33.9 0z"/>
                </svg>
            </div>
            <div class="voiceMidDiv">
<#--                <div class="voiceMidTitleDiv" id="voiceMidTitleDiv">我在听，请说话</div>-->
                <div class="voiceMidTitleDiv" id="voiceMidTitleDiv">点击按钮，开始录音</div>
<#--                <div class="voiceMidMsgDiv">点击下方，停止录音</div>-->
            </div>
            <div class="voiceWaveDiv">
                <div class="wave-container"></div>
            </div>
            <div class="voiceBottomDiv">
                <div class="voiceBottomLeftDiv">
                    <button id="voiceRecordBtn" onclick="voiceStartRecord();" class="btn btn-gray" data-loading-text="录音..." style="width: 100%;margin-left: 3px;" type="button">录音</button>
                    <button id="voicePauseBtn" onclick="voicePauseRecord();" class="btn btn-gray" disabled data-loading-text="暂停..." style="width: 100%;margin-left: 3px;" type="button">暂停</button>
                    <button id="voiceStopBtn" onclick="voiceStopRecord();" class="btn btn-gray" disabled data-loading-text="停止..." style="width: 100%;margin-left: 3px;" type="button">停止</button>
                    <button id="voicePlayBtn" onclick="voicePlayRecord();" class="btn btn-gray" disabled data-loading-text="播放..." style="width: 100%;margin-left: 3px;" type="button">播放</button>
                </div>
                <div class="voiceBottomRightDiv">
                    <button id="voiceCancelBtn" onclick="voiceCancel();" class="btn btn-gray" disabled data-loading-text="取消..." style="width: 100%;margin-left: 3px;" type="button">取消</button>
                    <button id="voiceSubmitBtn" onclick="voiceSubmitRecord();" class="btn btn-gray" disabled data-loading-text="上传中..." style="width: 100%;margin-left: 3px;" type="button">提交</button>
                </div>
            </div>
        </div>

        <!-- 图文vision多模态面板 -->
        <div class="row h" id="visionDiv" style="position: fixed;background-color: #fff;left: 50%;bottom: 0px;transform: translateX(-50%);width: inherit;margin-right: 0px;margin-left: 0px;height: 400px;border-top: 1px solid lightgray;">
            <div class="visionTopDiv">
                <button id="visionCancelBtn" onclick="visionCancel();" class="btn btn-gray" data-loading-text="取消..."  type="button">取消</button>
                <button id="visionSubmitBtn" onclick="imageSubmit();" class="btn btn-success" data-loading-text="上传中..."  type="button">提交</button>
            </div>
            <div class="visionModeDiv">
                <div class="form-check form-check-inline" style="display:inline;margin-left: 24px;">
                    <input class="form-check-input" type="radio" name="imageMode" id="imageModeVision" value="1" checked>
                    <label class="form-check-label" for="imageModeVision" style="font-weight: 400;">图文多模态</label>
                </div>
                <div class="form-check form-check-inline" style="display:inline;margin-left: 24px;">
                    <input class="form-check-input" type="radio" name="imageMode" id="imageModeText2Image" value="2">
                    <label class="form-check-label" for="imageModeText2Image" style="font-weight: 400;">文生图</label>
                </div>

            </div>
            <div class="visionTextDiv">
                <textarea id="visionTextArea" placeholder="说点什么试试吧"></textarea>
            </div>
            <div class="visionMidDiv" id="visionMidDiv">
                <div class="visionAddBtn" onclick="visionAddImage();">
                    <svg  class="vision-add-img-btn" xmlns="http://www.w3.org/2000/svg"  height="40" width="40" viewBox="0 0 448 512" fill="currentColor">
                        <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32V224H48c-17.7 0-32 14.3-32 32s14.3 32 32 32H192V432c0 17.7 14.3 32 32 32s32-14.3 32-32V288H400c17.7 0 32-14.3 32-32s-14.3-32-32-32H256V80z"/>
                    </svg>
                </div>
            </div>
        </div>
    </div>
    <div id="right-content" class="container1">
        <div class="right-content-header">
            <div class="right-content-header-title" id="right-content-header-title">相关知识片段</div>
            <div class="right-content-header-switch" onclick="rightPanelTriggle();">
                <svg xmlns="http://www.w3.org/2000/svg" class="right-panel-icon" width="18" height="18"  viewBox="0 0 512 512" fill="currentColor">
                    <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                    <path d="M142.9 142.9c62.2-62.2 162.7-62.5 225.3-1L327 183c-6.9 6.9-8.9 17.2-5.2 26.2s12.5 14.8 22.2 14.8H463.5c0 0 0 0 0 0H472c13.3 0 24-10.7 24-24V72c0-9.7-5.8-18.5-14.8-22.2s-19.3-1.7-26.2 5.2L413.4 96.6c-87.6-86.5-228.7-86.2-315.8 1C73.2 122 55.6 150.7 44.8 181.4c-5.9 16.7 2.9 34.9 19.5 40.8s34.9-2.9 40.8-19.5c7.7-21.8 20.2-42.3 37.8-59.8zM16 312v7.6 .7V440c0 9.7 5.8 18.5 14.8 22.2s19.3 1.7 26.2-5.2l41.6-41.6c87.6 86.5 228.7 86.2 315.8-1c24.4-24.4 42.1-53.1 52.9-83.7c5.9-16.7-2.9-34.9-19.5-40.8s-34.9 2.9-40.8 19.5c-7.7 21.8-20.2 42.3-37.8 59.8c-62.2 62.2-162.7 62.5-225.3 1L185 329c6.9-6.9 8.9-17.2 5.2-26.2s-12.5-14.8-22.2-14.8H48.4h-.7H40c-13.3 0-24 10.7-24 24z"/>
                </svg>
            </div>
        </div>
        <div class="nearest-div" style="padding: 5px;">
            <div class="row nearest-content" id="nearest-content">

            </div>
        </div>
        <div class="settings-div h" style="padding: 5px;">
            <div class="setting-item" onclick="useLocalKnowledge();">
                <div class="setting-item-content">
                    <div class="setting-item-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-r-square right-panel-icon" viewBox="0 0 16 16">
                            <path d="M5.5 4.002h3.11c1.71 0 2.741.973 2.741 2.46 0 1.138-.667 1.94-1.495 2.24L11.5 12H9.98L8.52 8.924H6.836V12H5.5V4.002Zm1.335 1.09v2.777h1.549c.995 0 1.573-.463 1.573-1.36 0-.913-.596-1.417-1.537-1.417H6.835Z"/>
                            <path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V2Zm15 0a1 1 0 0 0-1-1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2Z"/>
                        </svg>
                    </div>
                    <div class="setting-item-text">知识库模式</div>
                </div>
                <div class="setting-item-right">
                    <div class="setting-item-result" id="setting-item-local-result">否</div>
                    <div class="setting-item-icon">
                        <svg xmlns="http://www.w3.org/2000/svg"  width="18" height="18"  fill="currentColor" class="right-panel-icon" viewBox="0 0 320 512">
                            <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                            <path d="M278.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-160 160c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L210.7 256 73.4 118.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l160 160z"/>
                        </svg>
                    </div>
                </div>
            </div>

            <div class="setting-item h" id="setting-item-knowledge-result" onclick="openKnowledgeSelectDiv();">
                点击选择知识库
            </div>

            <div class="setting-item" onclick="useDocMode();">
                <div class="setting-item-content">
                    <div class="setting-item-icon">
                        <svg xmlns="http://www.w3.org/2000/svg"   width="18" height="18" fill="currentColor" class="right-panel-icon"  viewBox="0 0 384 512">
                            <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                            <path d="M64 464c-8.8 0-16-7.2-16-16V64c0-8.8 7.2-16 16-16H224v80c0 17.7 14.3 32 32 32h80V448c0 8.8-7.2 16-16 16H64zM64 0C28.7 0 0 28.7 0 64V448c0 35.3 28.7 64 64 64H320c35.3 0 64-28.7 64-64V154.5c0-17-6.7-33.3-18.7-45.3L274.7 18.7C262.7 6.7 246.5 0 229.5 0H64zm56 256c-13.3 0-24 10.7-24 24s10.7 24 24 24H264c13.3 0 24-10.7 24-24s-10.7-24-24-24H120zm0 96c-13.3 0-24 10.7-24 24s10.7 24 24 24H264c13.3 0 24-10.7 24-24s-10.7-24-24-24H120z"/>
                        </svg>
                    </div>
                    <div class="setting-item-text">文档模式</div>
                </div>
                <div class="setting-item-right">
                    <div class="setting-item-result" id="setting-item-doc-result">否</div>
                    <div class="setting-item-icon">
                        <svg xmlns="http://www.w3.org/2000/svg"  width="18" height="18"  fill="currentColor" class="right-panel-icon" viewBox="0 0 320 512">
                            <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                            <path d="M278.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-160 160c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L210.7 256 73.4 118.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l160 160z"/>
                        </svg>
                    </div>
                </div>
            </div>

            <div class="setting-item h" id="setting-item-file-upload" onclick="uploadDoc();">
                点击上传文档
            </div>

            <div class="setting-item" onclick="useNetMode();">
                <div class="setting-item-content">
                    <div class="setting-item-icon">
                        <svg xmlns="http://www.w3.org/2000/svg"  width="18" height="18" fill="currentColor" class="right-panel-icon" viewBox="0 0 512 512">
                            <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                            <path d="M352 256c0 22.2-1.2 43.6-3.3 64H163.3c-2.2-20.4-3.3-41.8-3.3-64s1.2-43.6 3.3-64H348.7c2.2 20.4 3.3 41.8 3.3 64zm28.8-64H503.9c5.3 20.5 8.1 41.9 8.1 64s-2.8 43.5-8.1 64H380.8c2.1-20.6 3.2-42 3.2-64s-1.1-43.4-3.2-64zm112.6-32H376.7c-10-63.9-29.8-117.4-55.3-151.6c78.3 20.7 142 77.5 171.9 151.6zm-149.1 0H167.7c6.1-36.4 15.5-68.6 27-94.7c10.5-23.6 22.2-40.7 33.5-51.5C239.4 3.2 248.7 0 256 0s16.6 3.2 27.8 13.8c11.3 10.8 23 27.9 33.5 51.5c11.6 26 20.9 58.2 27 94.7zm-209 0H18.6C48.6 85.9 112.2 29.1 190.6 8.4C165.1 42.6 145.3 96.1 135.3 160zM8.1 192H131.2c-2.1 20.6-3.2 42-3.2 64s1.1 43.4 3.2 64H8.1C2.8 299.5 0 278.1 0 256s2.8-43.5 8.1-64zM194.7 446.6c-11.6-26-20.9-58.2-27-94.6H344.3c-6.1 36.4-15.5 68.6-27 94.6c-10.5 23.6-22.2 40.7-33.5 51.5C272.6 508.8 263.3 512 256 512s-16.6-3.2-27.8-13.8c-11.3-10.8-23-27.9-33.5-51.5zM135.3 352c10 63.9 29.8 117.4 55.3 151.6C112.2 482.9 48.6 426.1 18.6 352H135.3zm358.1 0c-30 74.1-93.6 130.9-171.9 151.6c25.5-34.2 45.2-87.7 55.3-151.6H493.4z"/>
                        </svg>
                    </div>
                    <div class="setting-item-text">联网模式</div>
                </div>
                <div class="setting-item-right">
                    <div class="setting-item-result" id="setting-item-net-result">否</div>
                    <div class="setting-item-icon">
                        <svg xmlns="http://www.w3.org/2000/svg"  width="18" height="18"  fill="currentColor" class="right-panel-icon" viewBox="0 0 320 512">
                            <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                            <path d="M278.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-160 160c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L210.7 256 73.4 118.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l160 160z"/>
                        </svg>
                    </div>
                </div>
            </div>

            <div class="setting-item" id="setting-item-blank"></div>

            <div class="setting-item" onclick="useHistory(this);">
                <div class="setting-item-content">
                    <div class="setting-item-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-chat-right-text right-panel-icon" viewBox="0 0 16 16">
                            <path d="M2 1a1 1 0 0 0-1 1v8a1 1 0 0 0 1 1h9.586a2 2 0 0 1 1.414.586l2 2V2a1 1 0 0 0-1-1H2zm12-1a2 2 0 0 1 2 2v12.793a.5.5 0 0 1-.854.353l-2.853-2.853a1 1 0 0 0-.707-.293H2a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h12z"/>
                            <path d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6zm0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5z"/>
                        </svg>
                    </div>
                    <div class="setting-item-text">携带历史记录</div>
                </div>
                <div class="setting-item-right">
                    <div class="setting-item-result" id="setting-item-history-result">

                    </div>
                    <div class="setting-item-icon">
                        <svg xmlns="http://www.w3.org/2000/svg"  width="18" height="18"  fill="currentColor" class="right-panel-icon" viewBox="0 0 320 512">
                            <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2024 Fonticons, Inc.-->
                            <path d="M278.6 233.4c12.5 12.5 12.5 32.8 0 45.3l-160 160c-12.5 12.5-32.8 12.5-45.3 0s-12.5-32.8 0-45.3L210.7 256 73.4 118.6c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0l160 160z"/>
                        </svg>
                    </div>
                </div>
            </div>

            <div class="setting-item" onclick="removeConversation(this);">
                <div class="setting-item-content">
                    <div class="setting-item-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-trash right-panel-icon" viewBox="0 0 16 16">
                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                            <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                        </svg>
                    </div>
                    <div class="setting-item-text">清空聊天记录</div>
                </div>
            </div>

        </div>

    </div>
</div>
<div class="knowledge-select-container h">
    <div class="knowledge-edit-form">
        <div class="knowledge-edit-form-content">
            <div class="form-group">
                <div class="col col-flex-right">
                    <svg xmlns="http://www.w3.org/2000/svg" height="16" width="12" style="cursor: pointer;" viewBox="0 0 384 512" onclick="closeFormDiv();">
                        <!--!Font Awesome Free 6.5.1 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2023 Fonticons, Inc.-->
                        <path d="M342.6 150.6c12.5-12.5 12.5-32.8 0-45.3s-32.8-12.5-45.3 0L192 210.7 86.6 105.4c-12.5-12.5-32.8-12.5-45.3 0s-12.5 32.8 0 45.3L146.7 256 41.4 361.4c-12.5 12.5-12.5 32.8 0 45.3s32.8 12.5 45.3 0L192 301.3 297.4 406.6c12.5 12.5 32.8 12.5 45.3 0s12.5-32.8 0-45.3L237.3 256 342.6 150.6z"/>
                    </svg>
                </div>
            </div>
            <div class="form-group">
                <div class="col">
                    <h4>知识库选择</h4>
                </div>
            </div>

            <div class="form-group">
                <form class="form-inline">
                    <div class="col" id="knowledge-all" style="font-size: 16px;">

                    </div>
                </form>

            </div>
            <div class="form-group">
                <div class="col">
                    <h6 id="knowledge-select-warning-msg" style="color: red;"></h6>
                </div>
            </div>
            <div class="form-group">
                <div class="col col-flex-right">
                    <button type="button" class="btn btn-outline-danger btn-round" onclick="closeFormDiv();">取消</button>
                    <button type="button" class="btn btn-success btn-round" style="margin-left: 10px;" onclick="selectKnowledge();">确认</button>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="full-screen-bg" class="full-screen-bg h" onclick="hideSessionList();"></div>
<div id="alertMsgDiv" class="alert alert-success h" role="alert">
        <i class="fas fa-bullhorn" id="alertMsgText">123</i>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">×</span>
        </button>
</div>
<div id="previewImageDIv" class="full-screen-bg-preview h">
    <img src="" id="visionSelectedPreviewImage" class="visionSelectedPreviewImage">
    <button type="button" class="btn btn-outline-danger btn-round" onclick="closePreviewDiv();">关闭</button>
</div>
</body>
<script src="/static/js/chat.js"></script>
</html>