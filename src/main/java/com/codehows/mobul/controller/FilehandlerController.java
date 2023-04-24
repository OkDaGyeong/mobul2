package com.codehows.mobul.controller;

import com.codehows.mobul.dto.BoardsFileDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FilehandlerController {
    public List<BoardsFileDTO> parseFileInfo(Long boardID, List<MultipartFile> multipartFiles) throws Exception {
        List<BoardsFileDTO> fileList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            // 파일이 비어 있지 않을 때 작업을 시작해야 오류가 나지 않는다
            if (!multipartFile.isEmpty()) {
                // jpeg, png, gif 파일들만 받아서 처리할 예정
                String contentType = multipartFile.getContentType();
                String originalFileExtension;
                // 확장자 명이 없으면 이 파일은 잘 못 된 것이다
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    }
                    // 다른 파일 명이면 아무 일 하지 않는다
                    else {
                        break;
                    }

                    // 파일 저장 로직 추가
                    String fileName = UUID.randomUUID().toString() + originalFileExtension; // 저장할 파일명 생성 (랜덤 UUID + 확장자)
                    String uploadPath = "/image/"; // 파일 업로드 경로
                    File file = new File(uploadPath + fileName); // 파일 객체 생성
                    multipartFile.transferTo(file); // 파일을 서버에 저장

                    // 파일 정보를 DTO에 담아 fileList에 추가
                    BoardsFileDTO boardsFileDTO = new BoardsFileDTO();
                    boardsFileDTO.setFileId(boardID);
                    boardsFileDTO.setFileName(fileName);
                    boardsFileDTO.setFilePath(uploadPath);
                    // 파일 관련 정보를 DB에 저장하거나, fileList에 추가하는 로직 추가
                    // ...

                    // HTML에서 이미지 출력을 위해 이미지 파일의 URL 생성
                    String imageUrl = "/image/" + fileName; // 이미지 URL 생성
                    // 이미지 URL을 활용하여 HTML에 이미지 태그를 생성하거나, 이미지 출력 로직 추가
                    // ...
                }
            }
        }
        return fileList;
    }
}