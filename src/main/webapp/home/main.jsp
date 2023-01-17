<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--
 HomeMainServlet 에서 넘겨준 age=22 정보를 쓸 수 있음
 넘기면서 value는 Object로 넘겨주기 떄문에 맞는 자료형으로 변환해야 함
-->
<%
boolean isLogged = (boolean) request.getAttribute("isLogged");
int loggedMemberId = (int) request.getAttribute("loggedMemberId");
%>

<!doctype html>
<html lang="ko">
<head>
  <title>메인</title>
</head>
<body>
  <h1>메인 페이지</h1>

  <% if (isLogged) { %>
  <div>
    <%=loggedMemberId%>번 회원님 환영합니다.
    <a href="../member/doLogout">로그아웃</a>
  </div>
  <% } %>

  <% if (!isLogged) { %>
  <div>
    <a href="../member/login">로그인</a>
    <a href="../member/join">회원 가입</a>
  </div>
  <% } %>
  <div>
    <a href="../article/list">게시물 리스트</a>
  </div>
</body>
</html>