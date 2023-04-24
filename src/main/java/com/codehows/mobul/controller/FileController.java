package com.codehows.mobul.controller;

import com.codehows.mobul.entity.BoardsFile;
import com.codehows.mobul.repository.BoardsFileRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ws.mime.Attachment;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileController {

    private BoardsFileRepository boardsFileRepository;

    @GetMapping("/writer/{id}")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long id) throws IOException {
        BoardsFile boardsFile = boardsFileRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attachment ID"));

        Path path = Paths.get(boardsFile.getFilePath().toString());
        Resource resource = new UrlResource(path.toUri());

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + boardsFile.getFileOriName() + "\"")
                    .body(resource);
        } else {
            throw new RuntimeException("Could not read file: " + boardsFile.getFileOriName());
        }

    }

    @GetMapping("comment/{boardId}")
    public String myPage(Model model) {
        String fileName = "jk.jpg"; // 실제 파일 이름을 fileName 변수에 할당
        model.addAttribute("fileName", fileName); // fileName 변수를 모델에 추가하여 View에 전달
        return "comment/{boardId}"; // View의 이름을 리턴
    }



}
