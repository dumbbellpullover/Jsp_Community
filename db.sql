drop database if exists JSP_Community;
create database JSP_Community;
use JSP_Community;

# 게시글 테이블
create table article (
	id int unsigned not null primary key auto_increment,
    regDate datetime not null,
    updateDate datetime not null,
    title char(100) not null,
    body longtext not null
);

# 회원 테이블
create table member (
	id int unsigned not null primary key auto_increment,
    regDate datetime not null,
    updateDate datetime not null,
    loginId char(100) not null unique,
    loginPw char(100) not null,
    name char(100) not null
);

desc article;
desc member;

insert into article (regDate, updateDate, memberId, title, body)
values (now(), now(), 'title1', 1, 'body1');
INSERT INTO article (regDate, updateDate, memberId, title, body)
SELECT NOW(), NOW(), CONCAT('title__', RAND()), CONCAT('body__', RAND())
FROM article;

# 회원 테이블 데이터 생성
insert into member (regDate, updateDate, loginId, loginPw, name)
values (now(), now(), 'admin', 'admin', '관리자');
insert into member (regDate, updateDate, loginId, loginPw, name)
values (now(), now(), 'user1', 'user1', '유저1');
insert into member (regDate, updateDate, loginId, loginPw, name)
values (now(), now(), 'user2', 'user2', '유저2');
INSERT INTO member (regDate, updateDate, loginId, loginPw, name)
SELECT NOW(), NOW(), CONCAT('title__', RAND()), CONCAT('body__', RAND())
FROM article;
select * from member;

# 게시물 테이블에 memberId 컬럼 추가
ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL AFTER updateDate;

SELECT * FROM article ORDER BY id DESC LIMIT 1;

select * from article LIMIT 20, 20;
select count(*) from article;

# 번호 대신 이름으로 게시물 요청
SELECT A.*, M.name
FROM article AS A
JOIN member AS M
ON A.memberId = M.id;