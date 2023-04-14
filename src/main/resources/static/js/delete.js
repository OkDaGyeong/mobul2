// 댓글 목록에 대한 변수
const comListWrap = document.getElementById('comListWrap');

// 댓글 작성창에 대한 변수
const postArea = document.getElementById('postArea');
const postBtn = document.getElementById('post');

// 댓글 수에 대한 변수
let commentNum = 0;

window.onload = function(){
  // 댓글 삭제 버튼 클릭 시
  comListWrap.addEventListener('click', (event) => {
    const target = event.target;
    if (target.classList.contains('anonymousBtn')) {
      const commentItem = target.closest('.comList');
      comListWrap.removeChild(commentItem);
      //댓글 삭제 시 commentNum1 감소
      commentNum--;
      const commentNumEl = document.querySelector('.commentNum');
      commentNumEl.textContent = commentNum;
    }
  });
  // 작성 버튼 클릭 시
  postBtn.addEventListener('click', () => {
    // 작성 내용 가져오기
    const comment = postArea.value;
    console.log(comment);

    // 작성 내용이 비어있는 경우, 경고창 출력 후 함수 종료
    if (comment === '') {
      alert('댓글을 입력해주세요.');
      return;
    }

    // 새로운 댓글 항목 생성
    const newComment = document.createElement('li');
    newComment.classList.add('comList');

    // 익명 댓글 작성 창
    const anonymousWrap = document.createElement('div');
    anonymousWrap.classList.add('anonymousWrap');

    const dateWrap = document.createElement('div');
    dateWrap.classList.add('dateWrap');

    const anonymous = document.createElement('span');
    anonymous.classList.add('anonymous');
    anonymous.textContent = '익명';
    anonymousWrap.appendChild(anonymous);

    const anonymousBtn = document.createElement('button');
    anonymousBtn.classList.add('anonymousBtn');
    anonymousBtn.textContent = '삭제';
    anonymousWrap.appendChild(anonymousBtn);

    newComment.appendChild(anonymousWrap);

    // 댓글 내용
    const itemText = document.createElement('span');
    itemText.classList.add('itemText');
    itemText.textContent = comment;
    newComment.appendChild(itemText);

    // 댓글 작성일
    const date = new Date();
    const comDate = document.createElement('span');
    comDate.classList.add('comDate');
    comDate.textContent = `${date.getMonth() + 1}/${date.getDate()}`;
    dateWrap.appendChild(comDate);

    // 댓글 작성시간
    const comTime = document.createElement('span');
    comTime.classList.add('comTime');
    comTime.textContent = `${date.getHours()}:${date.getMinutes()}`;
    dateWrap.appendChild(comTime);

    newComment.appendChild(dateWrap);
    // 댓글 목록에 추가
    comListWrap.appendChild(newComment);

    // 작성 창 비우기
    postArea.value = '';



   // 댓글 작성한 갯수만큼 commentNum이 카운트 되게 해줘
    const commentNumEl = document.querySelector('.commentNum');
    commentNumEl.textContent = ++commentNum;
  });

}



// 댓글 삭제 버튼을 누르면 실행됩니다.
function deleteComment() {
  var comList = this.parentNode.parentNode;
  comList.parentNode.removeChild(comList);
}

// 작성하기 버튼에 이벤트를 추가합니다.
var postButton = document.getElementById("post");
postButton.addEventListener("click", postComment);

// 삭제 버튼에 이벤트를 추가합니다.
var delButtons = document.getElementsByClassName("delBtn");
for (var i = 0; i < delButtons.length; i++) {
  delButtons[i].addEventListener("click", deleteComment);
}



// 좋아요 버튼 클릭하면 카운트 된다
const likeBtn = document.querySelector('.likeBtn');
const likeNum = document.querySelector('.likeNum');

let count = 1;

likeBtn.addEventListener('click', () => {
  likeNum.textContent = count;
  count++;
});