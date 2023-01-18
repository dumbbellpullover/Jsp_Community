<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="메인 페이지"></c:set>
<%@ include file="../part/head.jspf"%>

<h1>메인 페이지</h1>
<%@ include file="../part/topBar.jspf"%>
<div>
  <a href="/usr/article/list">게시물 리스트</a>
</div>

<%@ include file="../part/foot.jspf"%>