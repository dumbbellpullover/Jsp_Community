package com.ukj.exam.controller;

import com.ukj.exam.Rq;
import com.ukj.exam.dto.ResultData;
import com.ukj.exam.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.ukj.exam.dto.Member;
import java.sql.Connection;

public class MemberController extends Controller {
  private HttpServletRequest req;
  private HttpServletResponse resp;
  private MemberService memberService;

  public MemberController(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
    this.req = req;
    this.resp = resp;

    this.memberService = new MemberService(conn);
  }


  @Override
  public void performAction(Rq rq) {
    switch (rq.getActionMethodName()) {
      case "join":
        actionShowJoin(rq);
        break;

      case "doJoin":
        actionDoJoin(rq);
        break;

      case "login":
        actionShowLogin(rq);
        break;

      case "doLogin":
        actionDoLogin(rq);
        break;

      case "doLogout":
        actionLogout(rq);
        break;

      default:
        rq.println("존재하지 않는 페이지입니다.");
    }

  }

  private void actionShowJoin(Rq rq) {
    rq.jsp("/member/join");
  }

  private void actionDoJoin(Rq rq) {

    String loginId = rq.getParam("loginId", "");
    String loginPw = rq.getParam("loginPw", "");
    String name = rq.getParam("name", "");

    boolean isJoinAvailableLoginId = memberService.getForPrintMemberByLoginId(loginId);


    if ( isJoinAvailableLoginId == false ) {
      rq.printf(String.format("<script> alert('%s 은(는) 이미 사용 중인 아이디입니다.'); " +
          "history.back(); </script>", loginId));
    } // history.back(); 은 form submit 하기 직전으로 돌아감.

    String redirectUri = rq.getParam("redirectUri", "/home/main");

    ResultData joinRd = memberService.join(loginId, loginPw, name);

    rq.replace(joinRd.getMsg(), redirectUri);
  }

  private void actionShowLogin(Rq rq) {
    rq.jsp("/member/login");
  }

  private void actionDoLogin(Rq rq) {

    String loginId = rq.getParam("loginId", "");
    String loginPw = rq.getParam("loginPw", "");

    boolean sameLoginIdLoginPw = memberService.isLoginIdLoginPwSame(loginId, loginPw);

    if (!sameLoginIdLoginPw) {
      rq.printf(String.format("<script> alert('일치하는 아이디나 비밀번호가 없습니다.'); " +
          "history.back(); </script>"));
      return;
    }

    Member member = memberService.getForPrintMemberByLoginIdLoginPw(loginId, loginPw);

    HttpSession session = req.getSession();
    session.setAttribute("loggedMemberId", member.getId());

    rq.printf(String.format(
        "<script> alert('로그인 성공'); " +
            "location.replace('/home/main'); </script>"
    )); // 이동하는 데에는 location.href(''), location.replace('')가 있음
    // replace는 흔적을 남기지 않고 이동

  }

  private void actionLogout(Rq rq) {
    HttpSession session = req.getSession();
    session.removeAttribute("loggedMemberId");

    rq.printf(String.format(
        "<script> alert('로그아웃 되었습니다.'); " +
            "location.replace('/home/main'); </script>"
    ));
  }

}
