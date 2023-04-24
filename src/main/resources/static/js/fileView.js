function toggleFileArea() {
  const fileArea = document.getElementById('fileArea');
  const btn = event.target;
  if (fileArea.style.display === 'none') {
    fileArea.style.display = 'block';
    btn.textContent = '첨부파일 닫기 ▲';
  } else {
    fileArea.style.display = 'none';
    btn.textContent = '첨부파일 열기 ▼';
  }
}


//첨부파일 뷰어로 보기
function fileViewer(fileOriName) {

  const fileUrl = '/files/'+fileOriName;
  const fileExtension = fileOriName.split('.').pop().toLowerCase();
//  console.log(fileUrl);
//  console.log(fileExtension)

  var w = 800;
  var h = 600;
  var left = (screen.availWidth - w) / 2;
  var top = (screen.availHeight - h) / 2;
  var windowFeatures = 'height=' + h + ',width=' + w + ',top=' + top + ',left=' + left;

   if (fileExtension === 'png' || fileExtension === 'jpg' || fileExtension === 'jpeg') {
   window.open(fileUrl, "이미지 미리보기 "+fileOriName, windowFeatures);

  } else if (fileExtension === 'mp4') {
   window.open(fileUrl, "영상 미리보기 "+fileOriName, 'height=600,width=800');

  } else if (fileExtension === 'mp3') {
    window.open(fileUrl,"음성 미리듣기"+ fileOriName, 'height=300,width=500');

  } else if (fileExtension === 'txt') {
    const xhttp = new XMLHttpRequest();
    xhttp.overrideMimeType("text/plain; charset=utf-8");
    xhttp.onreadystatechange = function() {
      if (this.readyState === 4 && this.status === 200) {
        const fileContent = this.responseText;
        // 파일 내용을 처리하는 코드
      }
    };
    xhttp.open('GET', fileUrl, true);
    xhttp.send();

    window.open(fileUrl,"텍스트 파일 미리보기 "+ fileOriName, 'height=600,width=800');
  } else {
    alert('미리보기를 지원하지 않는 파일입니다.');
  }
}
