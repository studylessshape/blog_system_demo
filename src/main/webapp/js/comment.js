var comment_content;
var comment_form;

function init_comment_js() {
    comment_content = document.getElementById("comment-content");
    comment_form = document.getElementById("add-comment");
}

function submit_comment() {
    if (comment_content.value.length < 0 || comment_content >= 255) {
        alert("评论不能为空/不能过长！");
        return;
    }

    comment_form.submit();
}