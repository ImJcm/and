function createPost() {
    let postTitle = $('#title').val();
    let postContents = $('#editor').val();

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
            alert("게시글 등록 성공");
            showPosts(1, pageSize);
        },
        error: function () {
            console.log('게시글 등록 error 실패');
        }
    });
}