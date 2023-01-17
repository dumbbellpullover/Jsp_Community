package com.ukj.exam.servlet;

import com.ukj.exam.util.DBUtil;
import com.ukj.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/article/delete")
public class ArticleDeleteServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Connection conn = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
      return;

    }

    String url = "jdbc:mysql://127.0.0.1:3306/jsp_community?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    String user = "guest";
    String password = "bemyguest";

    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset-utf-8");


    try {
      conn = DriverManager.getConnection(url, user, password);

//------------------------------------------------------

      int id =  Integer.parseInt(req.getParameter("id"));

      SecSql sql = SecSql.from("DELETE");
      sql.append("FROM article WHERE id = ?", id);

      DBUtil.delete(conn, sql);

      resp.getWriter().append(String.format("<script> alert('%d번 게시글이 삭제되었습니다.');" +
          "location.replace('list')</script>", id));

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
