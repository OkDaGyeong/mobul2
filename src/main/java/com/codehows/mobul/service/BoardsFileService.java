package com.codehows.mobul.service;


import com.codehows.mobul.entity.BoardsFile;
import com.codehows.mobul.repository.BoardsFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
@Service
@Transactional
@RequiredArgsConstructor
public class BoardsFileService {

    @Value("${fileLocation}")
    private String fileLocation;

    private final BoardsFileRepository boardsFileRepository;

    private final FileService fileService;

    // 작성페이지
    public void saveFile(BoardsFile boardsfile, MultipartFile multipartFile) throws Exception{
        String fileOriName = multipartFile.getOriginalFilename();
        String fileName = "";
        String fileUrl = "";

        // 파일 업로드
        if(!StringUtils.isEmpty(fileOriName)){
            fileName = fileService.uploadFile(fileLocation, fileOriName, multipartFile.getBytes());
            fileUrl = "/images/board/" + fileName;
        }

        // 상품 이미지 정보 저장
        boardsfile.updateFile(fileOriName, fileName, fileUrl);
        boardsFileRepository.save(boardsfile);
    }


    // 수정페이지 : 파일
    public void updateFile(Long fileId, MultipartFile file) throws Exception{
        if(!file.isEmpty()){
            BoardsFile savedFile = boardsFileRepository.findByFileId(fileId);
//                    .orElseThrow(EntityNotFoundException::new);

        // 기존 이미지 파일 삭제
        if(!StringUtils.isEmpty(savedFile.getFileName())) {
            fileService.deleteFile(fileLocation + "/" + savedFile.getFileName());
        }
        String fileOriName = file.getOriginalFilename();
        String fileName = fileService.uploadFile(fileLocation, fileOriName, file.getBytes());
        String fileUrl = "/images/board/" + fileName;
        savedFile.updateFile(fileOriName, fileName, fileUrl);
        }
    }

}
