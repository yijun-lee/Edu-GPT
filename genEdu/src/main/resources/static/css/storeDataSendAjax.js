function dataSend() {
    $('#quest').text($("#input").val());
    var data=$("#input").val();
    var messageDTO={
        result:data
    };
    $.ajax({
        url: "/dataSend",
//        url: "/gpt/quest",
        data: messageDTO,
        type:"POST",
    }).done(function (fragment) {
        $("#resultDiv").replaceWith(fragment);
//           $("#resultDiv").append(fragment);
    });
}

