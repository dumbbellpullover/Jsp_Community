package com.ukj.exam.service;

import com.ukj.exam.dao.MemberDao;
import com.ukj.exam.dto.ResultData;
import com.ukj.exam.util.Util;

import com.ukj.exam.dto.Member;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class MemberService {
  private MemberDao memberDao;
  public MemberService(Connection conn) {
    this.memberDao = new MemberDao(conn);
  }

  public boolean isLoginIdLoginPwSame(String loginId, String loginPw) {
    return memberDao.isLoginIdLoginPwSame(loginId, loginPw);
  }

  public boolean getForPrintMemberByLoginId(String loginId) {
    return memberDao.getForPrintMemberByLoginId(loginId);
  }

  public ResultData join(String loginId, String loginPw, String name) {
    memberDao.join(loginId, loginPw, name);

    return ResultData.from("S-1", Util.f("%s 회원님 환영합니다.", name), "name", name);
  }

  public Member getForPrintMemberByLoginIdLoginPw(String loginId, String loginPw) {
    return memberDao.getForPrintMemberByLoginIdLoginPw(loginId, loginPw);
  }
}
