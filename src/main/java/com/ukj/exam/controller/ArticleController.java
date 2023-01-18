package com.ukj.exam.controller;

import com.ukj.exam.Config;
import com.ukj.exam.Rq;
import com.ukj.exam.dto.Article;
import com.ukj.exam.dto.ResultData;
import com.ukj.exam.exception.SQLErrorException;
import com.ukj.exam.service.ArticleService;
import com.ukj.exam.util.DBUtil;
import com.ukj.exam.util.SecSql;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ArticleController extends Controller{
  private HttpServletRequest req;
  private HttpServletResponse resp;
  private ArticleService articleService;

  public ArticleController(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
    this.req = req;
    this.resp = resp;

    this.articleService = new ArticleService(conn);
  }

  @Override
  public void performAction(Rq rq) {
    switch (rq.getActionMethodName()) {
      case "list":
        actionList(rq);
        break;

      case "write":
        actionShowWrite(rq);
        break;

      case "doWrite":
        actionDoWrite(rq);
        break;
    }

  }

  public void actionList(Rq rq){
    //로직------------------------------------------------------

    int page = 1;
    if (req.getParameter("page") != null && req.getParameter("page").length() != 0) {
      page = Integer.parseInt(req.getParameter("page"));
    }

    int totalPage = articleService.getForPrintListTotalPage();
    List<Article> articles = articleService.getForPrintArticles(page);

    req.setAttribute("articles", articles);
    req.setAttribute("page", page);
    req.setAttribute("totalPage", totalPage);
    rq.jsp("/article/list");

    rq.print(articles.toString());

//------------------------------------------------------
  }

  public void actionShowWrite(Rq rq) {
    rq.jsp("/article/write");
  }

  public void actionDoWrite(Rq rq) {

    Connection conn = null;
    String driverName = Config.getDriverClassName();

    HttpSession session = req.getSession();

    if (session.getAttribute("loggedMemberId") == null) {
      rq.print(String.format("<script> alert('로그인 후 이용해주세요.');" +
          "location.replace('../member/login')</script>"));
      return;
    }

    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      rq.print("DB 드라이버 클래스 로딩 실패");
      return;

    }

    try {
      conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());

//------------------------------------------------------

      String title = req.getParameter("title");
      String body = req.getParameter("body");

      if (title.length() == 0) {
        rq.historyBack("제목을 입력해주세요.");
      }

      if (body.length() == 0) {
        rq.historyBack("내용을 입력해주세요.");
      }

      int loggedMemberId = (int) session.getAttribute("loggedMemberId");

      ResultData writeRd = articleService.write(title, body, loggedMemberId);

      rq.printf(writeRd.getMsg());

//      rq.print(String.format("<script> alert('%d번 게시글이 등록되었습니다.');" +
//          "location.replace('list')</script>", id));

//------------------------------------------------------

    } catch (SQLException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      rq.print("DB 드라이버 클래스 로딩 실패");
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
}
