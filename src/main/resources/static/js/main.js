const pageSize = 10;
const pageBtnSize = 5;

document.addEventListener("DOMContentLoaded",function () {
    const token = Cookies.get('Authorization');
    const host = "http://" + window.location.host;

    // 로그인 체크
    loginCheck(token);

    //초기화면
    showContests();
})

function loginCheck(token) {
    let html = ``;
    if(token == undefined) {
        html += `
            <button onClick="redirectToLogin()">Login / Signup</button>
        `;
    } else {
        let userEmailName;
        let userNickName;
        $.ajax({
            url:`/api/users`,
            type:'GET'
        })
            .done(function (response, status, xhr) {
                userEmailName = response['username'];
                userNickName = response['nickname'];

                html += `
                    <p>${userNickName}님 환영합니다.</p>
                `;
            })
            .fail(function (response) {
                alert(response.responseJSON.msg);
                window.location.href = "/";
            })

    }

    $('.auth-section').append(html);

}

function redirectToLogin() {
    window.location.href = "login.html";
}
// 공모전 카테고리 출력
function showContests() {
    var swiper = new Swiper('.swiper-container', {

        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        spaceBetween: 20, // Add some space between slides
        autoplay: {
            delay: 5000, // Set the delay in milliseconds (5000ms = 5 seconds)
            disableOnInteraction: false, // Allow autoplay even when user interacts with swiper
        },
    });

    let html = `
            <div class="swiper-container">
            <div class="swiper-wrapper">
    
                <div class="swiper-slide">
                    <img src="https://i.postimg.cc/yN8DBxHq/image.png" alt="Banner 1">
                </div>
                <div class="swiper-slide">
                    <img src="https://i.postimg.cc/4N5jDxNR/image.png" alt="Banner 2">
                </div>
                <!-- Add more swiper-slide divs with your images -->
                <div class="swiper-slide">
                    <img src="https://i.postimg.cc/yN8DBxHq/image.png" alt="Banner 3">
                </div>
    
            </div>
            <div class="swiper-button-prev"></div>
            <div class="swiper-button-next"></div>
        </div>
    
        <div class="container">
            <h2 class="section-title">List of Contests</h2>
            <ul class="contest-list">
                <li class="contest-item">
                    <div class="contest-details">
                        <h3>Contest Title</h3>
                        <p>Contest Description</p>
                    </div>
                </li>
                <!-- Add more contest items as needed -->
            </ul>
        </div>
    
        <div class="container">
            <h2 class="section-title">List of Boards</h2>
            <ul class="board-list">
                <li class="board-item">
                    <h3>Board Title</h3>
                    <p>Board Description</p>
                </li>
                <!-- Add more board items as needed -->
            </ul>
        </div>
    `;

    $('.main').empty();
    $('.main').append(html);
}

// 자유게시판 페이징 출력
function showPosts(page, size) {
    if(page <= 0) {
        page = 1;
    }

    $.ajax({
        type: 'GET',
        url: `/api/posts?page=${page}&size=${size}`,
    })
        .done(function (response, status, xhr) {
            let pages = response['totalPages'];

            if(page > pages) {
                page = pages;
            }

            let html = `
                    <div class="post-wrap">
                      <div class="post-title">
                        <strong>자유게시판</strong>
                        <p>자유로운 정보 공유와 공모전을 위한 커뮤니티입니다.</p>
                      </div>
                      <div class="post-list-wrap">
                          <div class="post-list">
                            <div class="top">
                              <div class="num">번호</div>
                              <div class="title">제목</div>
                              <div class="writer">작성자</div>
                              <div class="date">작성일</div>
                              <div class="count">조회수</div>
                            </div>`;

            let startNum = (page-1) * pageSize;
            response['content'].forEach((post) => {
                startNum += 1;
                html += `
                        <div>
                          <div class="num">${startNum}</div>
                          <div class="title"><a href="#">${post.title}</a></div>
                          <div class="writer">${post.writer}</div>
                          <div class="date">${post.modifiedDate}</div>
                          <div class="count">${post.communityPostViews}</div>
                        </div>
                    `;
            });

            html += `
                    </div>
                     <div class="post-btn">
                       <button>글쓰기</button>
                     </div>
                     <div class="post-page">
                        <a onclick="showPosts(1,pageSize)" class="bt first"><<</a>
                        <a onclick="showPosts(${page}-1,pageSize)" class="bt prev"><</a>
                `;

            let startPage = (Math.floor(page / pageBtnSize) * pageBtnSize) + 1;
            let endPage = (startPage + pageBtnSize) <= pages ? (startPage + pageBtnSize) : pages;

            for(let i=startPage;i<=endPage;i++) {
                if(page === i) {
                    html += `
                            <a onclick="showPosts(${i},pageSize)" class="num on">${i}</a>
                        `;
                } else {
                    html += `
                            <a onclick="showPosts(${i},pageSize)" class="num">${i}</a>
                        `;
                }
            }

            html += `
                            <a onclick="showPosts(${page}+1,pageSize)" class="bt first">></a>
                            <a onclick="showPosts(${pages},pageSize)" class="bt prev">>></a>
                          </div>
                      </div>
                    </div>
                `;
            $(".main").empty();
            $(".main").append(html);
        })
        .fail(function(response) {
            alert("자유게시글 조회 실패");
            console.log(response.responseJSON.msg);
        });
}

// // 1:1 채팅 화면 구성
function showChats() {
    $.ajax({
        type:'GET',
        url:`/api/chat/rooms`,
    })
        .done(function (response, status, xhr) {
            let html = `
                <div class="chat-wrap">
                    <div class="chat-room-list">
                        <div class="title">
                            <h2>채팅방 목록</h2>
                        </div>
                        <div class="room-lists">
            `;
            let roomList = response;
            roomList.forEach((room) => {
                html += `
                        <div class="room-list" id="room-${room.roomId}">
                            <a onclick="showChat(${room.roomId})">${room.chatroomName}</a>
                            <p>${room.createdDate}</p>
                        </div>
                    `;
            })

            html += `
                </div>
                    </div>
                    <div class="chat-room">
                    </div>
                </div>
            `;
            $('.main').empty();
            $('.main').append(html);
        })
        .fail(function (response) {
            console.log("채팅 목록 불러오기 실패");
            window.location.href = "/";
        })
}

// 채팅방으로 접속과 동시에 채팅 내역을 출력
function showChat(roomId) {
    $.ajax({
        type:'GET',
        url:`/api/chat/room?roomId=${roomId}`
    })
}