function scoring_quiz_ajax() {
    var quizDiv = document.getElementById("quiz_section");
    var quiz_cnt = 3;
    var obj = new Object();
    var ans_data = new Array();
    var ans_data_list;
    var answer;
    for(var i = 0;i<quiz_cnt;i++)
    {
        ans_data_list = new Object();
        ans_data_list.ans_no = i;
        answer = "#ans" + i.toString();
        ans_data_list.answer = $(answer + " option:selected") .val();
        ans_data.push(ans_data_list);
        obj.ans_data = ans_data;
    }
    $.ajax({
        url: "/scoring",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: "application/json",
        type:"POST",
        success : function(result){
            alert(result.score)
        },
        error: function(errorThrown) {
            alert(errorThrown.statusText);
        }
    });
}

