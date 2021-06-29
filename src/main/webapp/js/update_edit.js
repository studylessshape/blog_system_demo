var input_edit;
var output_edit;

function init_update_edit() {
    input_edit = document.getElementById("input-feild");
    output_edit = document.getElementById("output-feild");
}

function update_onchange() {
    output_edit.innerHTML = marked(input_edit.value);
    hljs.highlightAll();
}

function check_add_blog() {
    var blog_title = document.getElementById("blog-title").value;

    if (blog_title.length == 0) {
        alert('标题不能为空！');
        return;
    }

    // input_edit.value = input_edit.value.replace(new RegExp('"', 'g'), '\\\"');
    document.getElementById("add-blog-form").submit();
}