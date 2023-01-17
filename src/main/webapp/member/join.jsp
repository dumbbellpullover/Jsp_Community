<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>회원가입</title>
</head>
<body>
  <h1>회원가입</h1>

  <script>
    let JoinForm__submitDone = false;

    function JoinForm__submit(form) {

      if ( JoinForm__submitDone ) {
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

      form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
      if ( form.loginPwConfirm.value == 0 ) {
        alert('비밀번호를 다시 확인해주세요.');
        form.loginPwConfirm.focus();
        return;
      }

      if (form.loginPwConfirm.value != form.loginPwConfirm.value) {
        alert('비밀번호가 일치하지 않습니다.');
        form.loginPwConfirm.focus();
        return;
      }

      form.name.value = form.name.value.trim();
      if ( form.name.value == 0 ) {
        alert('이름을 입력해주세요.');
        form.name.focus();
        return;
      }

      form.submit();
      JoinForm__submitDone = true;
    }
  </script>

  <form action="doJoin" method="POST" onsubmit="JoinForm__submit(this); return false;"> <!-- onsumbit -> submit 했을 때 실행되는 것-->
    <!-- autocomplete = 자동 완성, placeholder="옅은 글씨"-->
    <div>ID: <input placeholder="아이디를 입력해주세요." name="loginId" type="text"></div>
    <div>PW: <input placeholder="비밀번호를 입력해주세요." name="loginPw" type="password"></div>
    <div>Confirm PW: <input placeholder="비밀번호를 다시 입력해주세요." name="loginPwConfirm" type="password"></div>
    <div>이름: <input placeholder="이름을 입력해주세요." name="name" type="text"></div>
    <div>
      <button type="submit">가입</button>
      <button type="button"> <!-- type="button" 을 하지 않으면 취소 버튼이 form으로 발송됨-->
        <a href="../home/main">취소</a>
      </button>
    </div>
  </form>
</body>
</html>