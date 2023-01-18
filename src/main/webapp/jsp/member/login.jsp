<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>로그인</title>
</head>
<body>
  <h1>로그인</h1>

  <script>
    let LoginForm__submitDone = false;

    function LoginForm__submit(form) {

      if ( LoginForm__submitDone ) {
        alert('처리 중입니다.');
      }

      form.loginId.value = form.loginId.value.trim();
      if ( form.loginId.value == 0 ) {
        alert('로그인 아이디를 입력해주세요.');
        form.loginId.focus();
        return;
      }

      form.loginPw.value = form.loginPw.value.trim();
      if ( form.loginPw.value == 0 ) {
        alert('로그인 비밀번호를 입력해주세요.');
        form.loginPw.focus();
        return;
      }

      form.submit();
      LoginForm__submitDone = true;
    }
  </script>

  <form action="doLogin" method="POST" onsubmit="LoginForm__submit(this); return false;"> <%-- onsumbit -> submit 했을 때 실행되는 것--%>
    <%-- autocomplete = 자동 완성, placeholder="옅은 글씨"--%>
    <div>ID: <input autocomplete="off" placeholder="아이디를 입력해주세요." name="loginId" type="text"></div>
    <div>PW: <input autocomplete="off" placeholder="비밀번호를 입력해주세요." name="loginPw" type="password"></div>
    <div>
      <button type="submit">로그인</button>
      <button type="button"> <%-- type="button" 을 하지 않으면 취소 버튼이 form으로 발송됨--%>
        <a href="../home/main">취소</a>
      </button>
    </div>
  </form>
</body>
</html>