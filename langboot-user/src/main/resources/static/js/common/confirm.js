function showConfirm(message, confirmCallback, cancelCallback) {
    let confirmModal = '<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">' +
        '<div class="modal-dialog" role="document">' +
        '<div class="modal-content">' +
        '<div class="modal-header">' +
        '<h5 class="modal-title" id="confirmModalLabel">提示</h5>' +
        '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
        '<span aria-hidden="true">&times;</span>' +
        '</button>' +
        '</div>' +
        '<div class="modal-body">' + message + '</div>' +
        '<div class="modal-footer">' +
        '<button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>' +
        '<button type="button" class="btn btn-success" id="confirmBtn">确认</button>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '</div>';

    $('body').append(confirmModal);
    $('#confirmModal').modal();

    $('#confirmBtn').on('click', function () {
        if (typeof confirmCallback == 'function') {
            confirmCallback();
        }
        $('#confirmModal').modal('hide');
    });

    $('#confirmModal').on('hidden.bs.modal', function () {
        $('#confirmModal').remove();
    });

    $('#confirmModal .close').on('click', function () {
        if (typeof cancelCallback == 'function') {
            cancelCallback();
        }
    });
}

function alertMsg(msg){
    $("#alertMsgText").text(msg);
    $("#alertMsgDiv").removeClass("h");
    setTimeout(function () {
        $("#alertMsgDiv").addClass("h");
    },1500);
}