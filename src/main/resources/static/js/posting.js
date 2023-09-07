function createPost() {
    console.log("js 진입")

    let postTitle = $('#title').val();
    let postContents = $('#summernote').val();

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
        data: JSON.stringify(data),
        success: function (xhr) {
            console.log(xhr);
            //console.log(response.id);
            alert("게시글 등록 성공");
            // location.reload();
            // window.location.href = `${window.location.origin}/home/mainpage`;
            window.history.back();
        },
        error: function () {
            console.log('게시글 등록 error 실패');
        }
    });
}