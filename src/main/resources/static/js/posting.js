$('#submitPost').click(function (event) {
    let authCookie = Cookies.get("Authorization");
    console.log("js 진입")

    event.preventDefault();
    let postTitle = $('#title').val();
    let postContents = $('#contents').val();

    let data = {
        title: postTitle,
        contents: postContents
    };

    console.log(postTitle);
    console.log(postContents);

    if (postTitle.trim() === '' || postContents.trim() === '') {
        alert('제목과 내용을 입력하세요');
        return;
    }

    $.ajax({
        url: '/api/posts',
        type: 'POST',
        contentType: 'application/json',
        headers: {
            'Authorization': authCookie
        },
        data: JSON.stringify(data),
        success: function (xhr) {
            console.log(xhr);
            //console.log(response.id);
            alert("게시글 등록 성공");
            // location.reload();
            //window.location.href = `${window.location.origin}/home/mainpage`;
        },
        error: function () {
            console.log('게시글 등록 error 실패');
        }
    });
});
