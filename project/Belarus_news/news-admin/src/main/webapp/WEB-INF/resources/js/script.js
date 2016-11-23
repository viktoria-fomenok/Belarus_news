function view(id) {
    document.getElementById("name" + id).removeAttribute("readonly");
    document.getElementById("edit" + id).hidden = true;
    document.getElementById("buttons" + id).hidden = false;
}
function hide(id) {
    document.getElementById("name" + id).setAttribute("readonly", "readonly");
    document.getElementById("edit" + id).hidden = false;
    document.getElementById("buttons" + id).hidden = true;
}
function updateTag(id) {
    document.forms["updateTag" + id].submit();
}
function deleteTag(id) {
    document.forms["deleteTag" + id].submit();
}

function updateAuthor(id) {
    document.forms["updateAuthor" + id].submit();
}
function expireAuthor(id) {
    document.forms["expireAuthor" + id].submit();
}
