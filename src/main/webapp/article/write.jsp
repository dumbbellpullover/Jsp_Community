<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시물 작성</title>
</head>
<body>
  <h1>게시물 작성</h1>
  <form action="doWrite" method="POST">
    <!-- autocomplete = 자동 완성, placeholder="옅은 글씨"-->
    <div>제목: <input autocomplete="off" placeholder="제목을 입력해주세요." name="title" type="text"></div>
    <div>내용: <textarea autocomplete="off" placeholder="내용을 입력해주세요." name="body"></textarea></div>
    <div>
      <button type="submit">작성</button>
      <a href="/article/list">리스트</a>
    </div>
  </form>
</body>
</html>