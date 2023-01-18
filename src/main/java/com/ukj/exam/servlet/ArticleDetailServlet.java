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

@WebServlet("/article/detail")
public class ArticleDetailServlet extends HttpServlet {
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

    try {
      conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());

      HttpSession session = req.getSession();
      boolean isLogged = false;
      int loggedMemberId = -1;
      Map<String, Object> loggedMemberRow = null;

      if (session.getAttribute("loggedMemberId") != null) {
        loggedMemberId = (int) session.getAttribute("loggedMemberId");
        isLogged = true;

        SecSql sql = SecSql.from("SELECT * FROM member");
        sql.append("WHERE id = ?", loggedMemberId);
        loggedMemberRow = DBUtil.selectRow(conn, sql);
      }

      req.setAttribute("isLogged", isLogged);
      req.setAttribute("loggedMemberId", loggedMemberId);
      req.setAttribute("loggedMemberRow", loggedMemberRow);

//------------------------------------------------------

      int id =  Integer.parseInt(req.getParameter("id"));

      SecSql sql = SecSql.from("SELECT *");
      sql.append("FROM article WHERE id = ?", id);

      Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);

      req.setAttribute("articleRow", articleRow);
      req.getRequestDispatcher("../article/detail.jsp").forward(req, resp);

      resp.getWriter().append(articleRow.toString());

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
