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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/member/doLogin")
public class MemberDoLoginServlet extends HttpServlet {
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

      SecSql sql = SecSql.from("SELECT *");
      sql.append("FROM member");
      sql.append("WHERE loginId = ? AND loginPW = ?", loginId, loginPw);

      Map<String, Object> memberRow = DBUtil.selectRow(conn, sql);

      if (memberRow.isEmpty()) {
        resp.getWriter().append(String.format("<script> alert('일치하는 아이디나 비밀번호가 없습니다.'); " +
            "history.back(); </script>"));

        return;
      }

      HttpSession session = req.getSession();
      session.setAttribute("loggedMemberId", memberRow.get("id"));

      resp.getWriter().append(String.format(
          "<script> alert('로그인 성공'); " +
              "location.replace('../home/main'); </script>"
      )); // 이동하는 데에는 location.href(''), location.replace('')가 있음
      // replace는 흔적을 남기지 않고 이동

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
