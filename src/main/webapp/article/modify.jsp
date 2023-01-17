<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>

<%
  Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시물 수정</title>
</head>
<body>
  <h1>게시물 수정</h1>

    <form action="doModify" method ="POST">

      <!-- detail?id= 에서 param value를 가져오는 세 가지 방법, 두번째는 EL 표기법-->
      <input name="id" type="hidden" value="<%= (int) articleRow.get("id")%>">
      <input autocomplete="off" type="hidden" name="id" value="${param.id}">
      <input name="id" type="hidden" value="<%= Integer.parseInt(request.getParameter("id"))%>">

      <div>번호: <%= (int) articleRow.get("id")%></div>
      <div>작성 날짜: <%= (String) articleRow.get("regDate")%></div>
      <div>최근 수정 날짜: <%= (String) articleRow.get("updateDate")%></div>
      <div>제목: <input name="title" autocomplete="off" type="text" placeholder="제목을 입력해주세요." value="<%= (String) articleRow.get("title")%>"></div>
      <div>내용: <textarea name="body" autocomplete="off" placeholder="내용을 입력해주세요."><%= (String) articleRow.get("body")%></textarea></div>
      <div>
        <button type="submit">수정</button>
        <a href="list">리스트</a>
      </div>
    </form>
</body>
</html>