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


function uploadAttach(){
    let id = $("#knowledge-id").val();
    let kid = $("#knowledge-kid").val();
    let uid = $("#knowledge-uid").val();
    let kname = $("#knowledge-name").val();
    let description = $("#knowledge-description").val();

    if (kname == null || kname.trim() == ''){
        $("#knowledge-warning-msg").html('知识库名称不能为空!');
        return;
    }
    if (description == null || description.trim() == ''){
        $("#knowledge-warning-msg").html('知识库描述不能为空!');
        return;
    }

    $.ajax({
        url: '/knowledge/save',
        type: 'POST',
        data: JSON.stringify({"id":id,"kid":kid,"uid":uid,"kname":kname,"description":description}),
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

}

function searchKnowledgeAttach(kid){
    let searchContent = $("#knowledge-attach-search-content").val();
    window.location.href = "/knowledge/attach?kid=" + kid +"&pageNum=" + attachListPageNum + "&searchContent=" + searchContent;
}

