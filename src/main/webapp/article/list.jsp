<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>

<%
  List< Map< String, Object> > articleRows = (List< Map < String, Object>>) request.getAttribute("articleRows");
%>
<!doctype html>
<html lang="ko">
<head>
  <title>게시물 리스트</title>
</head>
<body>
  <h1>게시물 리스트</h1>
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
      <td><a href="delete?id=<%= articleRow.get("id")%>">삭제</a></td>
    </tr>
    <% } %>
    </tbody>
  </table>
</body>
</html>