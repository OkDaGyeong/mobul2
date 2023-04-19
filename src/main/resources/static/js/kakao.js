    const btnKakao = document.querySelector('.btn-kakao');

btnKakao.addEventListener('click', (event) => {
  event.preventDefault(); // 버튼 클릭 시 기본 동작을 막음

  const url = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=f16a9a399cece2acaaf3110b8761e417&redirect_uri=http://localhost:9095/kakao/code&response_type=code&prompt=login";
  window.location.href = url; // 특정 URL로 이동
});