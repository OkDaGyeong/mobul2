package com.codehows.mobul.controller;

import com.codehows.mobul.dto.BoardsDTO;
import com.codehows.mobul.entity.Boards;
import com.codehows.mobul.service.BoardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class HomeController {
    // 기본 주소 요청 이 오면 띄워 주는 메서드
    @Autowired
    private BoardsService boardsService;

    @GetMapping("/")
    public String boardList(Model model, @PageableDefault(page=0, size=15, sort="boardId",
            direction= Sort.Direction.DESC) Pageable pageable, String searchTitle, String searchContent, String searchHashtag){

        //Page<Boards> list = boardsService.boardList(pageable);
        Page<Boards> list=null;
        if (searchTitle == null && searchContent==null && searchHashtag==null) {
            // 검색 단어가 없으면 기존 화면을 보여준다.
            list =boardsService.boardList(pageable);
        }else if(searchTitle !=null){
            list = boardsService.boardSearchList(searchTitle, pageable);
        }else if( searchContent !=null) {
            // 검색 단어가 들어오면 검색 단어에 맞게 나온다. 쿼리스트링으로 들어가는 키워드를 찾아냄
            list = boardsService.boardSearchList2(searchContent, pageable);
        }else if (searchHashtag !=null){
            list= boardsService.boardSearchList3(searchHashtag, pageable);
        }

        //페이지블럭 처리
//1을 더해주는 이유는 pageable은 0부터라 1을 처리하려면 1을 더해서 시작해주어야 한다.
        int nowPage = list.getPageable().getPageNumber() + 1;

//페이지네이션 개수 설정
        int pageCnt = 5;

//페이지네이션 범위 계산
        int startPage = Math.max(nowPage - (pageCnt / 2), 1);
        int endPage = Math.min(startPage + pageCnt - 1, list.getTotalPages());

//시작 페이지 재조정
        if (endPage == list.getTotalPages()) {
            startPage = Math.max(endPage - pageCnt + 1, 1);
        } else if (startPage == 1) {
            endPage = Math.min(startPage + pageCnt - 1, list.getTotalPages());
        }
        model.addAttribute("list", list);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }

}
