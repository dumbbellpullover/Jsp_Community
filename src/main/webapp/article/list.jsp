<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>

<%
  List< Map< String, Object> > articleRows = (List< Map < String, Object>>) request.getAttribute("articleRows");
  int cPage = (int) request.getAttribute("page");
  int totalPage = (int) request.getAttribute("totalPage");
%>
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시물 리스트</title>
</head>
<body>
  <h1>게시물 리스트</h1>
  <div>
    <a href="../article/write">게시물 작성</a>
  </div>
  <table border="1">
    <colgroup>
      <col width="50"/>
      <col width="200"/>
      <col width="200"/>
    </colgroup>
    <thead>
      <tr>
        <td><b>번호</b></td>
        <td><b>작성 날짜</b></td>
        <td><b>수정 날짜</b></td>
        <td><b>제목</b></td>
        <td><b>비고</b></td>
      </tr>
    </thead>
    <tbody>
    <% for (Map<String, Object> articleRow : articleRows) { %>
    <tr>
      <td><%= (int) articleRow.get("id")%></td>
      <td><%= articleRow.get("regDate")%></td>
      <td><%= articleRow.get("updateDate")%></td>
      <td> <a href="detail?id=<%= articleRow.get("id")%>"><%= articleRow.get("title")%></a></td>
      <td><a href="doDelete?id=<%= articleRow.get("id")%>">삭제</a></td>
      <td><a href="modify?id=<%= articleRow.get("id")%>">수정</a></td>
    </tr>
    <% } %>
    </tbody>
  </table>
  <style type="text/css">
    .page > a.red {
      color: red;
    }
  </style>
  <div class="page">
    <% for (int i = 1; i <= totalPage; i++) { %>
      <a class="<%=cPage == i ? "red" : ""%>" href="list?page=<%= i%>"><%= i%></a>
    <% } %>
  </div>
</body>
</html>