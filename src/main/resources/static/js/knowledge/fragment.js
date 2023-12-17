function openKnowledgeFragmentDiv(){
    $(".fragment-container").removeClass("h");
    $("#fragment-warning-msg").html('');
}

function closeFormDiv(){
    $(".fragment-container").addClass("h");
}

function addKnowledgeFragment(){
    openKnowledgeFragmentDiv();
    $("#knowledge-fragment").val('');
}


function saveKnowledgeFragment(){
    let fragment = $("#knowledge-fragment").val();

    if (fragment == null || fragment.trim() == ''){
        $("#fragment-warning-msg").html('知识片段内容不能为空!');
        return;
    }

    $.ajax({
        url: '/knowledge/fragment/save',
        type: 'POST',
        data: JSON.stringify({"kid":formKid,"content":fragment}),
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

function searchKnowledgeFragment(kid,docId){
    let searchContent = $("#knowledge-fragment-search-content").val();
    window.location.href = "/knowledge/fragment?kid=" + kid +"&docId=" + docId + "&kname=" + formKname +"&docName=" + formDocName +"&pageNum=" + fragmentListPageNum + "&searchContent=" + searchContent;
}

function removeKnowledgeFragment(kid,fid){
    showConfirm("确定要删除该知识片段吗？",function (){
        $.ajax({
            url: '/knowledge/fragment/remove',
            type: 'POST',
            data: JSON.stringify({"kid":kid,"fid":fid}),
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
    } ,null);

}