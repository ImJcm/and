const pageSize = 10;
const pageBtnSize = 5;
let loginUsername = undefined;
let stomp = null;
let UserId = null;

document.addEventListener("DOMContentLoaded", function () {
    const token = Cookies.get('Authorization');
    const host = "http://" + window.location.host;

    // 로그인 체크
    loginCheck(token);

    //초기화면
    showContests();
})

function loginCheck(token) {
    let html = ``;
    const authSection = $('.auth-section');

    if (token == undefined) {
        html += `
            <button class="auth-section-btn" onClick="redirectToLogin()">Login / Signup</button>
        `;
        authSection.empty();
        authSection.append(html);
    } else {
        let userEmailName;
        let userNickName;
        let userId;
        let check = unreadAlertCheck();

        $.ajax({
            type: 'GET',
            url: `/api/users`
        })
            .done(function (response, status, xhr) {
                UserId = userId = response['userId'];
                userEmailName = response['username'];
                loginUsername = userNickName = response['nickname'];

                // 읽지않은 알림이 있는지 체크
                if(check) {
                    html += `
                        <div class="alert-container">
                            <button class="alert-btn" type="button" onclick="showAlerts()" data-bs-toggle="collapse" data-bs-target="#collapseAlert" aria-expanded="false" aria-controls="collapseAlert"><i id="bellIcon" class="fa-solid fa-bell fa-bounce fa-lg" style="color: #e5e826;"></i></button>
                            <div class="collapse" id="collapseAlert">
                              <div class="card card-body alert-card-body">
                              </div>
                            </div>
                        </div>
                    `;
                } else {
                    html += `
                        <div class="alert-container">
                            <button class="alert-btn" type="button" onclick="showAlerts()" data-bs-toggle="collapse" data-bs-target="#collapseAlert" aria-expanded="false" aria-controls="collapseAlert"><i id="bellIcon" class="fa-solid fa-bell fa-lg" style="color: #000"></i></button>
                            <div class="collapse" id="collapseAlert">
                              <div class="card card-body alert-card-body">
                              </div>
                            </div>
                        </div>  
                    `;
                }

                html += `
                    <p>${userNickName}</p>
                    <button class="auth-section-btn" onclick="logout()">Logout</button>
                `;
                authSection.empty();
                authSection.append(html);

                alertConnection(userId);
            })
            .fail(function (response) {
                let errorMessage = response.responseJSON.errorMessage;
                alert("자유게시글 조회 실패 : " + errorMessage);
                if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                    window.location.href = "/view/login";
                } else if(errorMessage == "연결 오류!") {
                    alertConnection(UserId);
                }
            })
    }
}

// 읽지 않은 알림 체크
function unreadAlertCheck() {
    let check = false;
    $.ajax({
        type:'GET',
        url:`/api/notification`,
        async:false
    })
        .done(function (response, status, xhr) {
            let notifications = response;

            notifications.forEach((notification) => {
                if(!notification.isRead) {
                    check = true;
                    return;
                }
            })
        })
        .fail(function (response) {
            let errorMessage = response.responseJSON.errorMessage;
            alert("자유게시글 조회 실패 : " + errorMessage);
            if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                window.location.href = "/view/login";
            } else if(errorMessage == "연결 오류!") {
                alertConnection(UserId);
            }
        })
    return Boolean(check);
}

// 알림 버튼 클릭 시, 현재 접속한 사용자에게 전달된 알림 목록 조회
function showAlerts() {
    $.ajax({
        type:'GET',
        url:`/api/notification`
    })
        .done(function (response, status, xhr) {
            let notification = response;
            let html = `
                <div class="card-header alert-card-header">
                    <p>알림</p>
                    <button class="btn btn-outline-secondary alert-in-btn" id="all-read-alert" onclick="readAllAlert()">모든 알람 읽기</button>
                </div>
                <div class="alert-list-group">
            `;

            notification.forEach((notification) => {
                html += showAlert(notification);
            })

            html += `</div>`;

            $('.alert-card-body').empty();
            $('.alert-card-body').append(html);
        })

}

// 세부 알림 목록 조회
function showAlert(notification) {
    let notificationType = notification['notificationType'];
    let notificationId = notification['notificationId'];
    let isRead = notification['isRead'];
    let html = ``;

    if(notificationType == 'CHAT') {
        if(isRead) {
            html += `
                <div class="alert-list-group-item"id="alert-${notificationId}">
                    <div class="alert-item-header">
                        <div class="alert-item-header-check" onclick="readAlert('${notificationType}',${notificationId})" id="alert-header-check-${notificationId}">
                            <h5>${notificationType}</h5>
                            <i class="fa-solid fa-check" style="color: #2acb68;"></i>
                        </div>
                        <i class="fa-solid fa-xmark" style="color: #9fa0a3" onclick="deleteAlert(${notificationId})"></i>
                    </div>
                    <div class="alert-item-body">
                        <p style="color:#666666" onclick="showChats()">새로운 채팅이 도착하였습니다.</p>
                        <p style="color:#999999">${notification.content}</p>
                    </div>
                </div>
            `;
        } else {
            html += `
            <div class="alert-list-group-item" id="alert-${notificationId}">
                <div class="alert-item-header">
                    <div class="alert-item-header-check" onclick="readAlert('${notificationType}',${notificationId})" id="alert-header-check-${notificationId}">
                        <h5>${notificationType}</h5>
                    </div>
                    <i class="fa-solid fa-xmark" style="color: #9fa0a3" onclick="deleteAlert(${notificationId})"></i>
                </div>
                <div class="alert-item-body">
                    <p style="color:#666666">새로운 채팅이 도착하였습니다.</p>
                    <p style="color:#999999">${notification.content}</p>
                </div>
            </div>
        `;
        }

    } else {
        // 추후, 알림 Type을 고려했을 때 If 조건을 통해 검사하여 html 코드를 Type에 맞게 추가
    }

    return html;
}

// 읽지 않은 알림이 있는지 확인 후, Alert BellIcon 업데이트
function updateAlert() {
    let check = unreadAlertCheck();
    let bellIcon = document.getElementById("bellIcon");

    if(check) {
        bellIcon.classList.add("fa-bounce");
        bellIcon.style.color = "#e5e826";
    } else {
        bellIcon.classList.remove("fa-bounce");
        bellIcon.style.color = "#000";
    }
}

// 알림 읽음 처리
function readAlert(notificationType,notificationId) {
    $.ajax({
        type:'POST',
        url:`/api/notification/${notificationId}`
    })
        .done(function (response, status, xhr) {
            let type = notificationType;
            let id = notificationId;

            let html = `
                <h5>${type}</h5>
                <i class="fa-solid fa-check" style="color: #2acb68;"></i>
            `;
            $(`#alert-header-check-${id}`).empty();
            $(`#alert-header-check-${id}`).append(html);

            updateAlert();
        })
        .fail(function (response) {
            let errorMessage = response.responseJSON.errorMessage;
            alert("알림 읽기 실패 : " + errorMessage);
            if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                window.location.href = "/view/login";
            } else if(errorMessage == "연결 오류!") {
                alertConnection(UserId);
            }
        })
}

// 모든 알림 읽음 처리
function readAllAlert() {
    const alertItems = document.getElementsByClassName("alert-item-header-check");

    for (let i = 0; i < alertItems.length; i++) {
        alertItems[i].click();
    }
}

// 알림 삭제
function deleteAlert(notificationId) {
    $.ajax({
        type:'DELETE',
        url:`/api/notification/${notificationId}`
    })
        .done(function (response, status, xhr) {
            $(`#alert-${notificationId}`).remove();
            updateAlert();
        })
        .fail(function (response) {
            let errorMessage = response.responseJSON.errorMessage;
            alert("알람 삭제 실패 : " + errorMessage);
            if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                window.location.href = "/view/login";
            } else if(errorMessage == "연결 오류!") {
                alertConnection(UserId);
            }
        })
}

// 알림 SSE 연결
function alertConnection(userId) {
    let bellIcon = document.getElementById("bellIcon");
    const eventSource = new EventSource(`/api/notification/sub`);

    eventSource.addEventListener("sse", function (event) {
        // sse 연결 시도를 제외한 sse 통신 - sse 프로토콜 생성
        if(!event.data.includes("EventStream Created.")) {
            bellIcon.classList.add("fa-bounce");
            bellIcon.style.color = "#e5e826";
        }
    })
}

function logout() {
    Cookies.remove("Authorization");
    window.location.href = "/view/";
}

function redirectToLogin() {
    window.location.href = "/view/login";
}

// 공모전 카테고리 출력
function showContests() {
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
        <a></a>
        </div>

<div class="banner-text">📢 공모전 모아보기</div>

  <div class="image-boxes">
    <div class="image-box">
      <img src="https://i.postimg.cc/3xMRpGJj/display-1.jpg" alt="Image 1">
    </div>
    <div class="image-box">
      <img src="https://i.postimg.cc/Qd98dJLk/display.png" alt="Image 2">
    </div>
    <div class="image-box">
      <img src="https://i.postimg.cc/qB16twF9/display-2.jpg" alt="Image 3">
    </div>
    <div class="image-box">
      <img src="https://i.postimg.cc/Pr3ZkCBv/display-3.jpg" alt="Image 4">
    </div>
    <div class="image-box">
      <img src="https://i.postimg.cc/gJypv6vM/20230904-115038.png" alt="Image 5">
    </div>
    <div class="image-box">
      <img src="https://i.postimg.cc/VLRg0s2z/e3755407f5d1465089d57d79f35d3703.png" alt="Image 6">
    </div>
  </div>
    `;

    $('.main').empty();
    $('.main').append(html);

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
}

// 자유게시판 페이징 출력
function showPosts(page, size) {
    if (page <= 0) {
        page = 1;
    }

    $.ajax({
        type: 'GET',
        url: `/api/posts?page=${page}&size=${size}`,
    })
        .done(function (response, status, xhr) {
            let pages = response['totalPages'];

            if (page > pages) {
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

            let startNum = (page - 1) * pageSize;
            response['content'].forEach((post) => {
                startNum += 1;
                html += `
                        <div>
                          <div class="num">${startNum}</div>
                          <div class="title" id="post-${post.postId}"><a href="/onepost">${post.title}</a></div>
                          <div class="writer">${post.writer}</div>
                          <div class="date">${post.modifiedDate}</div>
                          <div class="count">${post.communityPostViews}</div>
                        </div>
                    `;
            });

            html += `
                    </div>
                     <div class="post-btn">
                     <button onclick="createPost()">글쓰기</button>
                     </div>
                     <div class="post-page">
                        <a onclick="showPosts(1,pageSize)" class="bt first"><<</a>
                        <a onclick="showPosts(${page}-1,pageSize)" class="bt prev"><</a>
                `;

            let startPage = (Math.floor(page / pageBtnSize) * pageBtnSize) + 1;
            let endPage = (startPage + pageBtnSize) <= pages ? (startPage + pageBtnSize) : pages;

            for (let i = startPage; i <= endPage; i++) {
                if (page === i) {
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
        .fail(function (response) {
            let errorMessage = response.responseJSON.errorMessage;
            alert("자유게시글 조회 실패 : " + errorMessage);
            if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                window.location.href = "/view/login";
            } else if(errorMessage == "연결 오류!") {
                alertConnection(UserId);
            }
        });
}

// 글쓰기(작성)
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

// // 1:1 채팅 화면 구성
function showChats() {
    $.ajax({
        type: 'GET',
        url: `/api/chat/rooms`,
    })
        .done(function (response, status, xhr) {
            let html = `
                <div class="chat-wrap">
                    <div class="chat-room-list">
                        <div class="title">
                            <h3>채팅방 목록</h3>
                            <button class="create-chat-menu-btn btn" type="button" data-bs-toggle="offcanvas"
                                data-bs-target="#createChatoffcanvasScrolling" aria-controls="offcanvasScrolling">
                                <i class="fas fa-solid fa-plus" aria-hidden="true"></i>
                            </button>

                            <div class="offcanvas offcanvas-end" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1"
                                 id="createChatoffcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
                                <div class="offcanvas-header">
                                    <h5 class="offcanvas-title" id="createChatoffcanvasScrollingLabel">채팅방 생성</h5>
                                    <button type="button" class="btn-close btn-close-black" data-bs-dismiss="offcanvas"
                                            aria-label="Close"></button>
                                </div>
                                <div class="offcanvas-body">
                                    <!-- chatroomName -->
                                    <div class="chatroom-name-wrap">
                                        <input type="text" class="chatroom-name-term" id="chatroom-name-term" placeholder="채팅방 이름을 입력하세요">
                                    </div>
                                    <!-- search -->
                                    <div class="invite-member-wrap">
                                        <div class="search-member">
                                            <input type="text" class="search-term" id="search-term" placeholder="초대할 맴버의 이메일을 입력하세요">
                                            <button type="submit" class="search-member-button">
                                                <i class="fa fa-search"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="member-list-wrapper" ng-app="app" ng-controller="MainCtrl as ctrl">
                                        <ul class="member-list" id="member-list">

                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="room-lists">
            `;
            let roomList = response;
            roomList.forEach((room) => {
                html += `
                        <div class="room-list" id="room-${room.roomId}">
                            <a onclick="showChat(${room.roomId})">${room.chatroomName}</a>
                            <p>${room.createdDate}</p>
                            <i class="fa-solid fa-xmark" onclick="deleteChat(${room.roomId})"></i>
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

            createChatroomHandler();
        })
        .fail(function (response) {
            let errorMessage = response.responseJSON.errorMessage;
            alert("채팅 목록 불러오기 실패 : " + errorMessage);
            if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                window.location.href = "/view/login";
            } else if(errorMessage == "연결 오류!") {
                alertConnection(UserId);
            }
        });
}

// 채팅 관련 Handler
function createChatroomHandler() {
    const memberInput = document.querySelector("#search-term");
    const memberSearchBtn = document.querySelector(".search-member-button");

    // 사용자 이메일 실시간 검색
    memberInput.addEventListener('keyup', function () {
        let search = $('#search-term').val().trim();
        let chatroomName = $('#chatroom-name-term').val().trim();

        if (search === "") {
            $('#member-list').empty();
            return;
        }

        $.ajax({
            type: 'GET',
            url: '/api/users/search',
            data: {
                keyword: search
            }
        })
            .done(function (response, status, xhr) {
                let users = response;
                let chatroomName = $('#chatroom-name-term').val().trim();
                let html = ``;

                users.forEach((user) => {
                    html += `
                        <li class="member-list-item">
                            <div class="member-list-item-content w-100">
                                <h4>${user.username}</h4>
                                <p>${user.nickname}</p>
                            </div>
                            <button class="member-setting-btn btn-primary" onclick="createChatroom('${chatroomName}',${user.userId})" type="button">
                                    <i class="fas fa-solid fa-plus" aria-hidden="true"></i>
                            </button>
                        </li>
                    `;
                })
                $('#member-list').empty();
                $('#member-list').append(html);
            })
            .fail(function (response, status, xhr) {
                let errorMessage = response.responseJSON.errorMessage;
                alert("사용자 검색 실패 : " + errorMessage);
                if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                    window.location.href = "/view/login";
                }
            })
    });

    // 사용자 이메일 검색
    memberSearchBtn.addEventListener('click', function () {
        let search = $('#search-term').val().trim();

        if (search === "") {
            $('#member-list').empty();
            return;
        }

        $.ajax({
            type: 'GET',
            url: '/api/users/search',
            data: {
                keyword: search
            }
        })
            .done(function (response, status, xhr) {
                let chatroomName = $('#chatroom-name-term').val().trim();
                let users = response;
                if (users.length == 0) {
                    alert("검색결과가 없습니다.");
                    return;
                }
                let html = ``;

                users.forEach((user) => {
                    html += `
                        <li class="member-list-item">
                            <div class="member-list-item-content w-100">
                                <h4>${user.username}</h4>
                                <p>${user.nickname}</p>
                            </div>
                            <button class="member-setting-btn btn-primary" onclick="createChatroom('${chatroomName}',${user.userId})" type="button">
                                    <i class="fas fa-plus" aria-hidden="true"></i>
                            </button>
                        </li>
                    `;
                })
                $('#member-list').empty();
                $('#member-list').append(html);
            })
            .fail(function (response, status, xhr) {
                let errorMessage = response.responseJSON.errorMessage;
                alert("사용자 검색 실패 : " + errorMessage);
                if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                    window.location.href = "/view/login";
                }
            })
    });
}

// 채팅방 생성
function createChatroom(chatroomName, userId) {
    $.ajax({
        type: 'POST',
        url: `/api/chat/room?chatroomName=${chatroomName}&participant=${userId}`
    })
        .done(function (response, status, xhr) {
            let room = response;

            let html = `
                <div class="room-list" id="room-${room.roomId}">
                    <a onclick="showChat(${room.roomId})">${room.chatroomName}</a>
                    <p>${room.createdDate}</p>
                    <i class="fa-solid fa-xmark" onclick="deleteChat(${room.roomId})"></i>
                </div>
            `;
            $('.room-lists').append(html);
        })
        .fail(function (response) {
            let errorMessage = response.responseJSON.errorMessage;
            alert("채팅방 생성 실패 : " + errorMessage);
            if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                window.location.href = "/view/login";
            }
        })
}

// 채팅방 삭제
function deleteChat(roomId) {
    $.ajax({
        type: 'DELETE',
        url: `/api/chat/room?roomId=${roomId}`
    })
        .done(function (response, status, xhr) {
            alert("채팅방 삭제 성공");
            $(`#room-${roomId}`).remove();
            $('.chat-room').empty();
        })
        .fail(function (response) {
            let errorMessage = response.responseJSON.errorMessage;
            alert("채팅방 삭제 실패 : " + response.responseJSON.errorMessage);
            window.location.href = "/view/";
        })
}

// 채팅방으로 접속과 동시에 채팅 내역을 출력
function showChat(roomId) {
    $.ajax({
        type: 'GET',
        url: `/api/chat/room?roomId=${roomId}`
    })
        .done(async function (response, status, xhr) {
            let roomInfo = response;
            let html = `
                <div class="chat-room-title">
                    <h2>${roomInfo.chatroomName}</h2>
                </div>
                <div class="chat-room-wrap">
                    <div class="loader loader--style1" title="0">
                      <svg version="1.1" id="loader-1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                       width="40px" height="40px" viewBox="0 0 40 40" enable-background="new 0 0 40 40" xml:space="preserve">
                      <path opacity="0.2" fill="#000" d="M20.201,5.169c-8.254,0-14.946,6.692-14.946,14.946c0,8.255,6.692,14.946,14.946,14.946
                        s14.946-6.691,14.946-14.946C35.146,11.861,28.455,5.169,20.201,5.169z M20.201,31.749c-6.425,0-11.634-5.208-11.634-11.634
                        c0-6.425,5.209-11.634,11.634-11.634c6.425,0,11.633,5.209,11.633,11.634C31.834,26.541,26.626,31.749,20.201,31.749z"/>
                      <path fill="#000" d="M26.013,10.047l1.654-2.866c-2.198-1.272-4.743-2.012-7.466-2.012h0v3.312h0
                        C22.32,8.481,24.301,9.057,26.013,10.047z">
                        <animateTransform attributeType="xml"
                          attributeName="transform"
                          type="rotate"
                          from="0 20 20"
                          to="360 20 20"
                          dur="0.5s"
                          repeatCount="indefinite"/>
                        </path>
                      </svg>
                    </div>
                </div>
                <div class="chatting-area">
                    <textarea id="msg"></textarea>
                    <button id="button-send">전송</button>
                </div>
            `;
            $('.chat-room').empty();
            $('.chat-room').append(html);

            await sleep(2);

            if (stomp != null) {
                stomp.disconnect();
            }

            showChatHistory(roomId);
        })
        .fail(function (response) {
            alert("채팅 불러오기 실패 : " + response.responseJSON.errorMessage);
            console.log(response);
            window.location.href = "/";
        });
}

// 채팅 내역 출력
function showChatHistory(roomId) {
    $.ajax({
        type: 'GET',
        url: `/api/chat/room/chatting?roomId=${roomId}`
    })
        .done(function (response, status, xhr) {
            $('.chat-room-wrap').empty();

            let chatHistories = response;

            chatHistories.forEach((chat) => {
                let chatId = chat.id;
                let writer = chat.writer;
                let message = chat.message;
                let messageType = chat.messageType;
                let sendDate = chat.sendDate;
                let html = ``;

                if (messageType == "enter" || messageType == "out") {
                    html += `
                        <div class="chat ch3">
                            <div class="textbox">${message}</div>
                            <div class="timebox">${sendDate}</div>
                        </div>
                    `;
                } else if(messageType == "message") {
                    if (writer == loginUsername) {
                        html += `
                        <div class="chat ch2" id="${chatId}">
                            <div class="icon"><i class="fa-solid fa-user"></i></div>
                            <div class="textbox">${message}</div>
                            <div class="timebox">${sendDate}</div>
                        </div>
                    `;
                    } else {
                        html += `
                        <div class="chat ch1" id="${chatId}">
                            <div class="icon"><i class="fa-solid fa-user"></i></div>
                            <div class="textbox">${message}</div>
                            <div class="timebox">${sendDate}</div>
                        </div>
                    `;
                    }
                }
                $(".chat-room-wrap").append(html);
            })

            connectChat(roomId);
        })
        .fail(function (response) {
            let errorMessage = response.responseJSON.errorMessage;
            alert("채팅내역 불러오기 실패 : " + response.responseJSON.errorMessage);
            if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                window.location.href = "/view/login";
            }
        });
}

// 채팅 연결
function connectChat(roomId) {
    var sockJs = new SockJS("/stomp-chat");
    //1. SockJS를 내부에 들고있는 stomp를 내어줌
    stomp = Stomp.over(sockJs);

    //2. connection이 맺어지면 실행
    stomp.connect({}, function () {
        console.log("STOMP Connection")

        //4. subscribe(path, callback)으로 메세지를 받을 수 있음
        stomp.subscribe("/sub/api/chat/room/" + roomId, function (chat) {
            let content = JSON.parse(chat.body);

            let chatId = chat.chatId;
            let writer = content.writer;
            let message = content.message;
            let messageType = content.messageType;
            let sendDate = content.sendDate;
            let html = ``;

            if (messageType == "enter" || messageType == "out") {
                html += `
                        <div class="chat ch3">
                            <div class="textbox">${message}</div>
                            <div class="timebox">${sendDate}</div>
                        </div>
                    `;
            } else if(messageType == "message") {
                if (writer == loginUsername) {
                    html += `
                        <div class="chat ch2" id="${chatId}">
                            <div class="icon"><i class="fa-solid fa-user"></i></div>
                            <div class="textbox">${message}</div>
                            <div class="timebox">${sendDate}</div>
                        </div>
                    `;
                } else {
                    html += `
                        <div class="chat ch1" id="${chatId}">
                            <div class="icon"><i class="fa-solid fa-user"></i></div>
                            <div class="textbox">${message}</div>
                            <div class="timebox">${sendDate}</div>
                        </div>
                    `;
                }
            }
            $(".chat-room-wrap").append(html);

            // 최신 메시지가 보이도록 스크롤 최신화
            $(".chat-room-wrap").scrollTop($('.chat-room-wrap')[0].scrollHeight);
        });

        //3. send(path, header, message)로 메세지를 보낼 수 있음
        stomp.send('/pub/api/chat/enter', {contentType: 'application/json'}, JSON.stringify({
            roomId: roomId,
            writer: loginUsername,
            messageType: "enter"
        }));
    });

    $("#button-send").on("click", function (e) {
        let msg = document.getElementById("msg");

        stomp.send('/pub/api/chat/message', {'content-type': 'application/json'}, JSON.stringify({
            roomId: roomId,
            message: msg.value,
            writer: loginUsername,
            messageType: "message"
        }));
        msg.value = '';
    })
        .fail(function (response) {
            let errorMessage = response.responseJSON.errorMessage;
            alert("채팅 전송 실패 : " + errorMessage);
            if(errorMessage == "유효하지 않은 토큰입니다" || errorMessage == "로그인이 필요합니다") {
                window.location.href = "/view/login";
            } else if(errorMessage == "연결 오류!") {
                alertConnection(UserId);
            }
        })
}

function sleep(sec) {
    return new Promise(resolve => setTimeout(resolve, sec * 1000));
}