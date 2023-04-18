package com.codehows.mobul.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "boards_file")
@Getter @Setter
public class BoardsFile {

    // 파일 고유 번호     pk  not null    auto_increment
    @Id
    @Column(name = "file_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;

    // 게시글 번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Boards fileBoardNum;

    // 저장 파일명
    @Column(name = "file_name")
    private String fileName;

    // 실제 파일명
    @Column(name = "file_ori_name")
    private String fileOriName;

    // 파일 조회 경로
    @Column(name = "file_path")
    private String filePath;

    public void updateFile(String fileOriName, String fileName, String filePath){
        this.fileOriName = fileOriName;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}