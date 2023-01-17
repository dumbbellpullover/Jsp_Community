package com.ukj.exam.servlet;

import com.ukj.exam.Config;
import com.ukj.exam.exception.SQLErrorException;
import com.ukj.exam.util.DBUtil;
import com.ukj.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/member/doJoin")
public class MemberDoJoinServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Connection conn = null;
    String driverName = Config.getDriverClassName();

    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
      return;

    }

    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset-utf-8");

    try {
      conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());

//------------------------------------------------------

      String loginId = req.getParameter("loginId");
      String loginPw = req.getParameter("loginPw");
      String name = req.getParameter("name");

      SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
      sql.append("FROM member");
      sql.append("WHERE loginId = ?", loginId);

      boolean isJoinAvailableLoginId = DBUtil.selectRowIntValue(conn, sql) == 0;

      if ( isJoinAvailableLoginId == false ) {
        resp.getWriter().append(String.format("<script> alert('%s 은(는) 이미 사용 중인 아이디입니다.'); " +
            "history.back(); </script>", loginId));
      } // history.back(); 은 form submit 하기 직전으로 돌아감.

      sql = SecSql.from("INSERT INTO member");
      sql.append("(regDate, updateDate, loginId, loginPw, name)");
      sql.append("VALUES");
      sql.append("(NOW(), NOW(), ?, ?, ?)", loginId, loginPw, name);

      //CONCAT('title__', RAND())

      int id = DBUtil.insert(conn, sql);

      resp.getWriter().append(String.format("<script> alert('%d번 회원이 가입되었습니다.');" +
          "location.replace('../home/main')</script>", id));

//------------------------------------------------------

    } catch (SQLException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
      return;

    } catch (SQLErrorException e) {
      e.getOrigin().printStackTrace();

    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();

        }

      } catch (SQLException e) {
        e.printStackTrace();

      }

    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }

}
