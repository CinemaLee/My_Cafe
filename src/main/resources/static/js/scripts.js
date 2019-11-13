
// 답글 추가 로직
$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault(); // 서버로 데이터가 안가게 막는다.
    console.log("click!!");

    var queryString = $(".answer-write").serialize(); // answer-write 클래스의 값을 읽어옴.
    console.log("query : " + queryString); // name="contents" 라는 key의 값.  ->  contents:hi~!!

    var url = $(".answer-write").attr("action"); // action속성의 값을 가져옴.
    console.log("url : " + url);

    $.ajax({ // 서버에 ajax로 데이터 전송.
      type : 'post',
      url : url, // 여기로 보냄
      data : queryString,
      dataType : 'json',
      error : onError, // 보내는데 에러발생하면
      success : onSuccess // 성공하면
    });
}

//==== 동적 html 생성==========//
function onSuccess(data, status) {
    console.log(data);
    if(!data.resultCode) {
        alert(data.description);
    }

    var answerTemplate = $("#answerTemplate").html();
    var template = answerTemplate.format(data.responseData.user.userId, data.responseData.formattedCreatedAt, data.responseData.contents, data.responseData.question.id, data.responseData.id); // 각각 {0}번 {1번}..에 들어감
    $(".qna-comment-slipp-articles").prepend(template); // append는 오름차순. 뒤로보내기.
    $(".answer-write textarea").val("");
}


function onError() {
    console.log("error");
}

////////////


// 삭제 로직
$(".link-delete-comment").click(deleteAnswer);
function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var url = deleteBtn.attr("href");
    console.log("url : "+url);

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        error : function (xhr, status) {
            console.log("error");
        },
        success : function (data, status) {
            console.log(data);
            if(data.resultCode) {
                deleteBtn.closest("article").remove();
            }else{
                alert(data.description);
            }
        }
    });
}




/////////////////////////////




//===== 동적 html을 생성하는 부분 =====//
String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
