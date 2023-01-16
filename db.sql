create database JSP_Community;
use JSP_Community;

create table article (
	id int unsigned not null primary key auto_increment,
    regDate datetime not null,
    updateDate datetime not null,
    title char(100) not null,
    body longtext not null
);

desc article;
select * from article;

insert into article (regDate, updateDate, title, body)
values (now(), now(), 'title1', 'body1');
insert into article (regDate, updateDate, title, body)
values (now(), now(), 'title2', 'body2');
insert into article (regDate, updateDate, title, body)
values (now(), now(), 'title3', 'body3');

select * from article;