function openSpecialNounDiv(){
    $(".special-container").removeClass("h");
    $("#special-warning-msg").html('');
}

function closeFormDiv(){
    $(".special-container").addClass("h");
}

function addSpecialNoun(){
    openSpecialNounDiv();
    $("#special-id").val('');
    $("#special-content").val('');
    $("#special-pinyin").val('');
    $("#special-sort").val('');
}

function editSpecialNoun(specialNoun){
    openSpecialNounDiv();
    $("#special-id").val(specialNoun.id);
    $("#special-content").val(specialNoun.content);
    $("#special-pinyin").val(specialNoun.pinyin);
    $("#special-sort").val(specialNoun.sort);
}

function saveSpecialNoun(){
    let id = $("#special-id").val();
    let content = $("#special-content").val();
    let pinyin = $("#special-pinyin").val();
    let sort = $("#special-sort").val();

    if (content == null || content.trim() == ''){
        $("#special-warning-msg").html('专业名词不能为空!');
        return;
    }
    if (pinyin == null || pinyin.trim() == ''){
        $("#special-warning-msg").html('拼音不能为空!');
        return;
    }
    if (sort == null || sort.trim() == ''){
        $("#special-warning-msg").html('排序不能为空!');
        return;
    }

    $.ajax({
        url: '/special/save',
        type: 'POST',
        data: JSON.stringify({"id":id,"content":content,"pinyin":pinyin,"sort":sort}),
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

function removeSpecialNoun(id) {
    showConfirm("确定要删除该专业名词吗？",function (){
        $.ajax({
            url: '/special/remove',
            type: 'POST',
            data: JSON.stringify({"id":id}),
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

function searchSpecialNoun(){
    let searchContent = $("#special-search-content").val();
    window.location.href = "/special/index?pageNum=" + specialNounListPageNum + "&searchContent=" + searchContent;
}

