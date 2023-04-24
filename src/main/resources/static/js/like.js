function plzLogin() {
    alert("로그인후 이용해주세요.");
}

function likeCheck(boardId,isLiked) {

    /* isLiked == 0 좋아요를 누르지않은상태
     * isLiked == 1 좋아요를 누른상태
     */

    console.log(boardId+"  "+isLiked);

    /* CREATE */
    if(isLiked === false){
        /* ajax */
        const baseUrl = "http://localhost:9095";
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* POST 방식으로 요청 */
//        httpRequest.open('POST', baseUrl + "/board/comment/"+boardId+"/like");
        httpRequest.open('POST', baseUrl + "/like/"+boardId);

        /* ResponseType Json */
        httpRequest.responseType = "json";

        /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
        httpRequest.send();

        /* httpRequest 상태 감지 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                if (httpRequest.status === 200) {
                    window.location.reload();
                } else {
                    let error = httpRequest.response;
                    console.log(error.message);
                }
            }

        }
    }
    /* DELETE */
    else {
        /* ajax */
        const baseUrl = "http://localhost:9095";
        /* XMLHttpRequest 객체 정의 */
        httpRequest = new XMLHttpRequest();

        /* POST 방식으로 요청 */
//        httpRequest.open('DELETE', baseUrl + "/board/comment/"+boardId+"/like");
        httpRequest.open('DELETE', baseUrl + "/like/"+boardId);
        /* ResponseType Json */
        httpRequest.responseType = "json";

        /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
        httpRequest.send();

        /* httpRequest 상태 감지 */
        httpRequest.onreadystatechange = () => {
            /* readyState가 Done이고 응답 값이 200(ok) 일때 받아온 boolean으로 분기 */
            if (httpRequest.readyState === XMLHttpRequest.DONE) {
                if (httpRequest.status === 200) {
                    window.location.reload();
                } else {
                    let error = httpRequest.response;
                    console.log(error.message);
                }
            }

        }
    }
}