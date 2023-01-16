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
  <ul>
    <%
      for (Map<String, Object> articleRow : articleRows) {
    %>
    <li>
      <a href="detail?id=<%= articleRow.get("id")%>">
      <%= (int) articleRow.get("id")%>번,
      <%= articleRow.get("regDate")%>,
      <%= articleRow.get("updateDate")%>,
      <%= articleRow.get("title")%>
      </a>
    </li>
    <% } %>
  </ul>
</body>
</html>