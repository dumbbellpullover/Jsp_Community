package com.ukj.exam.servlet;

import com.ukj.exam.Config;
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
import java.util.List;
import java.util.Map;

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {
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

//------------------------------------------------------

      int page = 1;
      if (req.getParameter("page") != null && req.getParameter("page").length() != 0) {
          page = Integer.parseInt(req.getParameter("page"));
      }

      int itemInAPage = 20;
      int limitFrom = (page - 1) * itemInAPage;

      SecSql sql = SecSql.from("SELECT COUNT(*)");
      sql.append("FROM article");

      int totalPage = (int) Math.ceil( (double) DBUtil.selectRowIntValue(conn, sql) / itemInAPage);

      sql = SecSql.from("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");
      sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

      List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

      req.setAttribute("articleRows", articleRows);
      req.setAttribute("page", page);
      req.setAttribute("totalPage", totalPage);
      req.getRequestDispatcher("../article/list.jsp").forward(req, resp);

      resp.getWriter().append(articleRows.toString());

//------------------------------------------------------

    } catch (SQLException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
      return;

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
