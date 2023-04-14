// 댓글 등록 버튼을 눌렀을 때
document.addEventListener('submit', function(e) {
  e.preventDefault(); // 기본 동작 막기

  // 이하 코드는 submit 이벤트 핸들러 내부에서 처리합니다.
  // ...
});

// 대댓글 작성 버튼을 눌렀을 때
var commentList = document.getElementById('comListWrap');

commentList.addEventListener('click', function(e) {
  if (e.target && e.target.className == 'anonymousBtn') {
    // 대댓글 입력창 생성
    var parentComment = e.target.parentNode.parentNode;
    var commentId = parentComment.getAttribute('id');
    var replyForm = document.createElement('form');
    replyForm.setAttribute('id', 'reply-form-' + commentId);
    replyForm.setAttribute('class', 'reply-form');
    replyForm.innerHTML = `
      <div class="anonymousWrap">
        <span class="anonymous">익명</span>
      </div>
      <textarea class="reply-textarea" placeholder="대댓글을 작성해주세요"></textarea>
      <div class="button-wrap">
        <button class="reply-submit" data-comment-id="${commentId}">작성하기</button>
        <button class="reply-cancel">취소하기</button>
      </div>
    `;

    // 대댓글 입력창을 댓글 아래에 추가
    var replyWrap = parentComment.querySelector('.reply-wrap');
    if (!replyWrap) {
      replyWrap = document.createElement('div');
      replyWrap.setAttribute('class', 'reply-wrap');
      parentComment.appendChild(replyWrap);
    }
    replyWrap.appendChild(replyForm);

    // 폼 요소와 서버를 연결
    var replySubmitBtn = replyForm.querySelector('.reply-submit');
    replySubmitBtn.addEventListener('click', function(e) {
      e.preventDefault(); // 기본 동작 막기
      // 서버로 데이터 전송하는 코드
      // ...
    });

    // 스크롤을 아래로 내려서 대댓글 입력창이 보이게 함
    replyForm.scrollIntoView({ behavior: 'smooth', block: 'start' });
  } else if (e.target && e.target.className == 'reply-cancel') {
    // 취소 버튼 클릭시 대댓글 입력창 제거
    var replyForm = e.target.parentNode.parentNode;
    replyForm.remove();
  }
});

document.addEventListener('click', function(e) {
  if (e.target && e.target.className == 'reply-submit') {
    // 대댓글 작성 내용 가져오기
    var commentId = e.target.getAttribute('data-comment-id');
    var replyForm = document.querySelector(`#reply-form-${commentId}`);
    var replyTextarea = replyForm ? replyForm.querySelector('.reply-textarea') : null;
    if (replyTextarea) {
      // 대댓글 객체 생성
      var replyText = replyTextarea.value.trim();
      var reply = {
        id: Date.now(),
        commentId: commentId,
        text: replyText,
        date: new Date(),
        author: '익명'

      };

      // 대댓글 HTML 생성
      var replyHTML = `
        <li class="comList reply">
          <div class="anonymousWrap">
            <span class="anonymous">${reply.author}</span>
          </div>
          <span class="itemText">${reply.text}</span>
          <div class="replyInfo">
            <span class="replyDate">${reply.date}</span>
            <button class="replyDeleteBtn">삭제</button>
            <button class="replyModifyBtn">수정</button>
            <button class="replyBtn">대댓글</button>
          </div>
        </li>`;

      // 대댓글 HTML을 댓글 리스트에 추가
      var commentList = document.getElementById('comListWrap');
      var parentComment = document.getElementById(commentId);

      // 대댓글 입력창 제거
      replyForm.remove();
    }
  }
});