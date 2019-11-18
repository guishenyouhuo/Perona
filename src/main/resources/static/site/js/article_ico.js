$(function () {
    var bgIcoList = new Array("book", "game", "note", "chat", "code", "image", "web", "link", "design", "lock");
    var baseIco = "bg-ico-";
    var index = 0;
    $(".item-meta-ico").each(function () {
        $(this).removeClass("bg-ico-book");
        $(this).addClass(baseIco + bgIcoList[index]);
        if (++index >= bgIcoList.length) {
            index = 0;
        }
    });
});

