<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="com.ukj.exam.dto.Article"%>

<%
  Article article = (Article) request.getAttribute("article");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시물 상세 페이지</title>
</head>
<body>
  <h1>게시물 상세 페이지</h1>
  <%@ include file="../part/topBar.jspf"%>
    <table>
      <div>번호: <%= (int) article.id%></div>
      <div>작성 날짜: <%= (String) article.regDate%></div>
      <div>최근 수정 날짜: <%= (String) article.updateDate%></div>
      <div>제목: <%= (String) article.title%></div>
      <div>내용: <%= (String) article.body%></div>
      <div>
        <a href="modify?id=<%= (int) article.id%>">수정</a>
        <!-- web-app 2.3버전이라 ${param.id}를 못 씀, 2.5 버전 이상만 가능-->
        <a href="doDelete?id=<%= (int) article.id%>">삭제</a>
        <a href="list">리스트</a>
      </div>
    </table>
</body>
</html>