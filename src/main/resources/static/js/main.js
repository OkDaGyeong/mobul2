function resetSearch() {
        // 검색어와 선택된 카테고리 초기화

        document.querySelector('option[value="title"]').selected = true;
        document.getElementById("searchKeyword").value = "";
        window.location.href = '/'
    }

    //
 document.addEventListener("DOMContentLoaded", function() {
  const searchCategory = document.getElementById("searchCategory");
  const searchKeyword = document.getElementById("searchKeyword");

  // searchCategory 값이 변경될 때마다 searchKeyword input의 name 속성 변경
  searchCategory.addEventListener("change", function() {
    if (searchCategory.value === "title") {
      searchKeyword.setAttribute("name", "searchTitle");
    } else if (searchCategory.value === "content") {
      searchKeyword.setAttribute("name", "searchContent");
    } else {
      searchKeyword.setAttribute("name", "searchHashtag");
    }

    // 선택된 옵션을 localStorage에 저장
    localStorage.setItem("searchCategory", searchCategory.value);
  });

  // 이전에 선택한 옵션이 있다면 선택된 옵션을 유지
//  const savedSearchCategory = localStorage.getItem("searchCategory");
//  if (savedSearchCategory) {
//    searchCategory.value = savedSearchCategory;
//
//    // 선택된 옵션에 따라서 검색어 input의 name 속성을 변경
//    if (searchCategory.value === "title") {
//      searchKeyword.setAttribute("name", "searchTitle");
//    } else if (searchCategory.value === "content") {
//      searchKeyword.setAttribute("name", "searchContent");
//    } else {
//      searchKeyword.setAttribute("name", "searchHashtag");
//    }
//  }

// 현재 URL에서 쿼리 문자열 추출
const queryString = window.location.search;

// URL의 쿼리 문자열을 파싱하여 객체로 변환
const urlParams = new URLSearchParams(queryString);

// category 값 가져오기
const category = urlParams.get("category");

// 가져온 category 값을 사용하여 select 요소의 옵션 선택
const select = document.getElementById("searchCategory");

for (let option of select.options) {
  if (option.value === category) {
    option.selected = true; // category 값과 일치하는 옵션 선택
    break;
  }
}


  // 검색어 유지
  const searchTitle = new URLSearchParams(window.location.search).get("searchTitle");
  if (searchTitle) {
    searchKeyword.value = searchTitle;
  }

  const searchContent = new URLSearchParams(window.location.search).get("searchContent");
  if (searchContent) {
    searchKeyword.value = searchContent;
  }

  const searchHashtag = new URLSearchParams(window.location.search).get("searchHashtag");
  if (searchHashtag) {
    searchKeyword.value = searchHashtag;
  }
});
