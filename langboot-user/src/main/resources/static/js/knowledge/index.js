function openKnowledgeDiv(){
    $(".knowledge-container").removeClass("h");
    $("#knowledge-warning-msg").html('');
}

function closeFormDiv(){
    $(".knowledge-container").addClass("h");
}

function addKnowledge(){
    openKnowledgeDiv();
    $("#knowledge-id").val('');
    $("#knowledge-kid").val('');
    $("#knowledge-uid").val('');
    $("#knowledge-name").val('');
    $("#knowledge-description").val('');
}

function editKnowledge(knowledge){
    openKnowledgeDiv();
    $("#knowledge-id").val(knowledge.id);
    $("#knowledge-kid").val(knowledge.kid);
    $("#knowledge-uid").val(knowledge.uid);
    $("#knowledge-name").val(knowledge.kname);
    $("#knowledge-description").val(knowledge.description);
}

function saveKnowledge(){
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

function removeKnowledge(kid) {
    showConfirm("确定要删除该知识库吗？",function (){
        $.ajax({
            url: '/knowledge/remove',
            type: 'POST',
            data: JSON.stringify({"kid":kid}),
            dataType: 'json',
            contentType: 'application/json',
            success: function(data) {
                console.log(data);
                window.location.reload();
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
    },null);
}

function searchKnowledge(){
    let searchContent = $("#knowledge-search-content").val();
    window.location.href = "/knowledge/index?pageNum=" + knowledgeListPageNum + "&searchContent=" + searchContent;
}

