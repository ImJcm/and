    // let authCookie = Cookies.get("Authorization");
$.ajax({
    url: `/api/posts/${postId}`,
    type: 'GET',
    contentType: 'application/json',
    headers: {
        'Authorization': authCookie
    },
    success: function (xhr) {
        console.log('게시글 1개 GET 요청 성공');
        console.log(xhr);
        $('.post-main').empty();

        let card_html = `
                                    <div class="one-post" id="getOnePost">
                                        <div class="card mb-3" style="width: 50rem;">
                                            <div class="row g-0" id="goclick">
                                                <div class="col-md-1">
                                                    <div class="myprofile"></div>
                                                </div>
                                                <div class="col-md-8">
                                                    <div class="card-body">
                                                        <h5 class="writer-nickname">${xhr.nickname}</h5>
                                                        <p class="card-text" id="post-content-paragraph">${xhr.content}</p>
                                                        <input type="text" class="form-control" id="edit-post-content" value="${xhr.content}" style="display: none;">
                                                        <p class="card-text"><small class="text-muted">수정시각</small></p>
                                                        <!-- 버튼 -->
                                                        <button id="editPostBtn">수정</button>
                                                        <button id="savePostBtn" style="display: none;">저장</button>
                                                        <button id="deletePostBtn">삭제</button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="card-footer">
                                                <p style="color:red">❤</p>
                                                <p class="card-text">${xhr.likeCnt}</p>
                                                <button id="postLike">좋아요</button>
                                                <button id="deleteLike">좋아요 취소</button>
                                            </div>
                                        </div>
                                    </div>
                `
        $('.post-main').append(card_html);
    }
})