function make_quiz_ajax() {
    var quizDiv = document.getElementById("quiz_section");
    var quiz_cnt = 3;
    var difficulty = $('#difficultyType option:selected').val();
    var quiz_type = $('#quizType option:selected').val();
    var obj = {"quiz_cnt": quiz_cnt, "difficulty": difficulty, "quiz_type": quiz_type};
    $.ajax({
        url: "/exercise",
        data: JSON.stringify(obj),
        dataType: "json",
        contentType: "application/json",
        type:"POST",
        success : function(result){
            var div='';
            for(var i = 0;i<quiz_cnt;i++){
                div = '';
                if (quiz_type == 0)
                {
                    div = div + '<div class = "quiz_detail"><p>'+ result['quiz'+i.toString()].problem +'</p>';
                    div = div + '<p>' + result['quiz'+i.toString()].example1 + '</p>';
                    div = div + '<p>' + result['quiz'+i.toString()].example2 + '</p>';
                    div = div + '<p>' + result['quiz'+i.toString()].example3 + '</p>';
                    div = div + '<p>' + result['quiz'+i.toString()].example4 + '</p>';
                    div = div + '<select name = "1" class="answer_list" id = "ans' + i.toString() + '"><option value="a">a</option><option value="b">b</option><option value="c">c</option><option value="d">d</option></select>';
                    $('#quiz_section').append(div);
                }
                else if(quiz_type == 2) $('#quiz_section').append('<div class = "quiz_detail"><p>'+ result['quiz'+i.toString()].problem +'</p><p>True</p><p>False</p><select name = "1" class="answer_list" id = "ans' + i.toString() + '"><option value="True">True</option><option value="False">False</option></select></div>');
            }
        },
        error: function(errorThrown) {
            alert(errorThrown.statusText);
        }
    });
}

