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
select * from article;
desc member;
select * from member;

insert into article (regDate, updateDate, title, body)
values (now(), now(), 'title1', 'body1');
insert into article (regDate, updateDate, title, body)
values (now(), now(), 'title2', 'body2');
insert into article (regDate, updateDate, title, body)
values (now(), now(), concat('title__', rand()), concat('body__', rand()));
INSERT INTO article (regDate, updateDate, title, body)
SELECT NOW(), NOW(), CONCAT('title__', RAND()), CONCAT('body__', RAND())
FROM article;

SELECT * FROM article ORDER BY id DESC LIMIT 1;

select * from article LIMIT 20, 20;
select count(*) from article;