function make_ppt_ajax() {
    var qnaDiv = document.getElementById("qna_section");
    var data=$("#input").val();
    var obj = {"pageCnt": $("#pptPageCnt").val(), "target": $("#pptTarget").val(), "head": $("#pptHead").val(), "subHead": $("#pptSubHead").val(), "presenter": $("#pptPresenter").val()};
    $.ajax({
        url: "/makePpt",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: "application/json",
        type:"POST",
        success : function(result){
            console.log(result);

        },
        error: function(errorThrown) {
            alert(errorThrown.statusText);
        }
    });
}

