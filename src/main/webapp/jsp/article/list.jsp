<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="게시물 목록"></c:set>
<%@ include file="../part/head.jspf"%>

<h1>게시물 리스트</h1>
<%@ include file="../part/topBar.jspf"%>

<div>
  <a href="/home/main">홈으로 이동</a>
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
  <c:forEach items="${articles}" var="article">
    <tr>
      <td>${article.id}</td>
      <td>${article.regDate}</td>
      <td>${article.updateDate}</td>
      <td><a href="detail?id=${article.id}">${article.title}</a></td>
      <td>
        <a onclick="if (confirm('삭제하시겠습니까?') == false) return false;" href="doDelete?id=${article.id}">삭제</a>
        <a href="modify?id=${article.id}">수정</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<style type="text/css">
    .page > a.red {
      color: red;
    }

</style>
<div class="page">
  <%-- 원래는 이렇게 가져왔지만 EL을 써서 편하게 가져옴 --%>
  <%-- int cPage = (int) request.getAttribute("page"); --%>
  <c:set var="cPage" value="${page}"/>
  <c:set var="totalPage" value="${totalPage}"/>
  <c:set var="pageMenuSize" value="5"/>
  <c:set var="from" value="${cPage - pageMenuSize}"/>
  <c:set var="end" value="${cPage + pageMenuSize}"/>

  <c:if test="${cPage > 1}">
    <a href="list?page=1">◄</a>
  </c:if>

  <c:set var="start" value="${from < 1 ? 1 : from}"/>
  <c:set var="end" value="${end > totalPage ? totalPage : end}"/>

  <c:forEach var="i" begin="${start}" end="${end}" step="1">
    <c:set var="aClassRed" value="${cPage == i ? 'red' : ''}"/>
    <a class="${aClassRed}" href="list?page=${i}">${i}</a>
  </c:forEach>


  <c:if test="${cPage < totalPage}">
    <a href="../article/list?page=${totalPage}">►</a>
  </c:if>
</div>

<%@ include file="../part/foot.jspf"%>