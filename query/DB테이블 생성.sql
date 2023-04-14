
drop table users;
drop table boards;
drop table boards_file;
drop table replies;
drop table comments;


CREATE TABLE users(                             	 -- 사용자 테이블
  user_id varchar(20) NOT NULL primary key,		   	 -- 사용자 아이디    일 대 일
  user_password VARCHAR(20) NOT NULL,         		 -- 사용자 비밀번호         
  user_phone VARCHAR(20) NULL,						 -- 사용자 전화번호
  user_one varchar(20),								 -- 더미
  user_two varchar(20),								 -- 더미
  user_three varchar(20)							 -- 더미
);

CREATE TABLE boards(                              
  board_id INT primary key not null AUTO_INCREMENT,			-- 게시글 번호  pk  not null Auto_increment  일 대 다 
  board_title VARCHAR(50) NOT NULL,              		    -- 게시글 제목 
  board_content text not NULL,								-- 게시글 본문 
  board_view int default 0,									-- 게시글 조회수 
  board_writer varchar(20) not null,						-- 게시글 작성자 FK 설정 not tull, users테이블의 user_id와 연결
  board_like int DEFAULT 0,									-- 게시글 좋아요 not null default 0 
  board_tag varchar(30),									-- 게시물 해시태그 
  borad_date timestamp,										-- 게시글 작성날짜
  board_one varchar(20) null,
  board_two varchar(20) null,
  board_three int(10) null,
  board_four int(10) null,
  
  -- 유저스  테이블 있는  pk(user_id)를 borads 테이블의 외래키(board_writer <<- 얘를 외래키로 )로 사용한다 
      CONSTRAINT fk_board_user   -- 별칭
        FOREIGN KEY (board_writer) REFERENCES users(user_id)
        ON DELETE CASCADE    -- 삭제 되면 같이 삭제
        on update cascade	 -- 업데이트 되면 같이 업데이트
);



-- boards_file table 생성 

CREATE TABLE boards_file(
    file_id INT PRIMARY KEY not null AUTO_INCREMENT,			-- 파일 고유 번호 pk not null Auto_increment
    file_board_num INT not null,    							-- int not null  FK 설정  boards 테이블 board_id와 연결 
    file_img BLOb,												-- 파일 이미지	 	blob	
    file_name varchar(100),										-- 파일 명		varchar
    file_path varchar(100),										-- 파일 경로		varchar
    file_size bigint,											-- 파일 크기		bingint
    file_one int(10),
    file_two varchar(10),
    file_three varchar(15),


-- file_board_num 을 board_id의 외래키로 설정   별칭을 fk_file_board_num으로 설정
    CONSTRAINT fk_file_board_id   -- 별칭 
        FOREIGN KEY (file_board_num) REFERENCES boards(board_id)
        ON DELETE CASCADE
        on update cascade
);


-- 댓글 테이블
Create table comments(
	comment_id int primary key AUTO_INCREMENT, 								-- 댓글 고유 번호 int pk not null auto_increment
    comment_content text not null,											-- 댓글 내용 text not null
    comment_writer varchar(20) 	not null,									-- varchar(20) not null			
    comment_date timestamp,         										-- timestamp
    comment_board_num int not null, 										-- int not null  fk 설정 boards 테이블의 board_id와 연결
    comment_one varchar(10),
	comment_two int(10),
    comment_three varchar(20),
    
    -- comment_board_num 을 board_id의 외래키로 설정  별칭을 fk_comment_board_num 으로 설정
	CONSTRAINT fk_comment_board_num		-- 별칭
        FOREIGN KEY (comment_board_num) REFERENCES boards(board_id)
        ON DELETE CASCADE
        on update cascade

);

-- 대댓글 테이블
create table replies(
	reply_id int PRIMARY key auto_increment,							-- 대댓글 고유 번호 int pk not null auto_increment
    reply_content text not null,
    reply_writer varchar(20) not null,
    reply_date timestamp,
    reply_comment_num int not null,  									-- 댓글의 고유 번호  int not null fk 설정 comments 테이블의 comment_id와 연결
	CONSTRAINT fk_reply_comment_num   -- 별칭
        FOREIGN KEY (reply_comment_num) REFERENCES comments(comment_id)
        ON DELETE CASCADE
        on update cascade
);    
