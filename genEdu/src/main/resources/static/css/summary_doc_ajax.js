function summary_doc_ajax() {
    var data=$("#inputPath").val();
    var obj = {"file_path": data};
//    var messageDTO={
//        result:data
//    };
    $.ajax({
        url: "/summary",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: "application/json",
        type:"POST",
        success : function(result){
            console.log(result.summary);
            $('#sum_text').text(result.summary);
        },
        error: function(errorThrown) {
            alert(errorThrown.statusText);
        }
    })
}




