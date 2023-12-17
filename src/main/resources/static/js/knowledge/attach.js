function openKnowledgeAttachDiv(){
    $(".attach-container").removeClass("h");
    $("#attach-warning-msg").html('');
}

function closeFormDiv(){
    $(".attach-container").addClass("h");
}

function addKnowledgeAttach(){
    openKnowledgeAttachDiv();
    $("#knowledge-id").val('');
    $("#knowledge-kid").val('');
    $("#knowledge-uid").val('');
    $("#knowledge-name").val('');
    $("#knowledge-description").val('');
}

function removeKnowledgeAttach(kid,docId){
    showConfirm("确定要删除该附件吗？",function (){
        $.ajax({
            url: '/knowledge/attach/remove',
            type: 'POST',
            data: JSON.stringify({"kid":kid,"docId":docId}),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                closeFormDiv();
                window.location.reload();
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
    },null);

}

function uploadAttach(kid){
    let formData = new FormData();
    formData.append("kid",kid);
    formData.append('file', $('input[type=file]')[0].files[0]);
    $.ajax({
        url: '/knowledge/attach/upload',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
            closeFormDiv();
            window.location.reload();
        },
        error: function(xhr, status, error) {
            // 处理错误
        }
    });
}

function searchKnowledgeAttach(kid){
    let searchContent = $("#knowledge-attach-search-content").val();
    window.location.href = "/knowledge/attach?kid=" + kid +"&kname=" + formKname +"&pageNum=" + attachListPageNum + "&searchContent=" + searchContent;
}

