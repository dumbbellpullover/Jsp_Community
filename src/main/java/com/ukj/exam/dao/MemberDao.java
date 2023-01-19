package com.ukj.exam.dao;

import com.ukj.exam.dto.Member;
import com.ukj.exam.util.DBUtil;
import com.ukj.exam.util.SecSql;

import java.sql.Connection;
import java.util.Map;

public class MemberDao {

  private Connection conn;
  public MemberDao(Connection conn) {
    this.conn = conn;
  }
  public boolean getForPrintMemberByLoginId(String loginId) {
    SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
    sql.append("FROM member");
    sql.append("WHERE loginId = ?", loginId);

    return DBUtil.selectRowIntValue(conn, sql) == 0;
  }

  public void join(String loginId, String loginPw, String name) {
    SecSql sql = SecSql.from("INSERT member");
    sql.append("(regDate, upDateDate, loginId, loginPw, name)");
    sql.append("VALUES");
    sql.append("(NOW(), NOW(), ?, ?, ?)", loginId, loginPw, name);

    DBUtil.insert(conn, sql);
  }

  public boolean isLoginIdLoginPwSame(String loginId, String loginPw) {
    SecSql sql = SecSql.from("SELECT COUNT(*) > 0");
    sql.append("FROM member");
    sql.append("WHERE loginId = ? AND loginPW = ?", loginId, loginPw);

    return DBUtil.selectRowBooleanValue(conn, sql);
  }

  public Member getForPrintMemberByLoginIdLoginPw(String loginId, String loginPw) {
    SecSql sql = SecSql.from("SELECT *");
    sql.append("FROM member");
    sql.append("WHERE loginId = ? AND loginPW = ?", loginId, loginPw);

    return new Member(DBUtil.selectRow(conn, sql));
  }
}
