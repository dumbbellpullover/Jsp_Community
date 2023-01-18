package com.ukj.exam.controller;

import com.ukj.exam.Config;
import com.ukj.exam.Rq;
import com.ukj.exam.dto.Article;
import com.ukj.exam.dto.ResultData;
import com.ukj.exam.exception.SQLErrorException;
import com.ukj.exam.service.ArticleService;
import com.ukj.exam.util.DBUtil;
import com.ukj.exam.util.SecSql;
import com.ukj.exam.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ArticleController extends Controller {
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

      case "detail":
        actionDetailList(rq);
        break;

      case "write":
        actionShowWrite(rq);
        break;

      case "doWrite":
        actionDoWrite(rq);
        break;

      case "doDelete":
        actionDoDelete(rq);
        break;

      default:
        rq.println("존재하지 않는 페이지입니다.");
    }

  }

  private void actionDoDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    String redirectUri = rq.getParam("redirectUri", "../article/list");

    if(id == 0) {
      rq.historyBack("id를 입력해주세요");
      return;
    }

    Article article = articleService.getForPrintArticleById(id);

    if (article == null) {
      rq.historyBack(Util.f("%번 게시물이 존재하지 않습니다.", id));
      return;
    }

    ResultData deleteRd = articleService.delete(id);

    rq.replace(deleteRd.getMsg(), redirectUri);

  }

  private void actionDetailList(Rq rq) {

    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      rq.historyBack("id를 입력해주세요.");
      return;
    }

    Article article = articleService.getForPrintArticleById(id);

    rq.setAttr("article", article);
    rq.jsp("/article/detail");

    rq.print(article.toString());
  }

  public void actionList(Rq rq) {

    int page = 1;
    if (req.getParameter("page") != null && req.getParameter("page").length() != 0) {
      page = Integer.parseInt(req.getParameter("page"));
    }

    int totalPage = articleService.getForPrintListTotalPage();
    List<Article> articles = articleService.getForPrintArticles(page);

    rq.setAttr("articles", articles);
    rq.setAttr("page", page);
    rq.setAttr("totalPage", totalPage);
    rq.jsp("/article/list");

    rq.print(articles.toString());

  }

  public void actionShowWrite(Rq rq) {
    rq.jsp("/article/write");
  }

  public void actionDoWrite(Rq rq) {

    HttpSession session = req.getSession();

    if (session.getAttribute("loggedMemberId") == null) {
      rq.print(String.format("<script> alert('로그인 후 이용해주세요.');" +
          "location.replace('../member/login')</script>"));
      return;
    }

    String title = req.getParameter("title");
    String body = req.getParameter("body");

    String redirectUri = rq.getParam("redirectUri", "../article/list");

    if (title.length() == 0) {
      rq.historyBack("제목을 입력해주세요.");
    }

    if (body.length() == 0) {
      rq.historyBack("내용을 입력해주세요.");
    }

    int loggedMemberId = (int) session.getAttribute("loggedMemberId");

    ResultData writeRd = articleService.write(title, body, loggedMemberId);
    int id = (int) writeRd.getBody().get("id");

    redirectUri = redirectUri.replace("[NEW_ID]", id + "");

    rq.replace(writeRd.getMsg(), redirectUri);

  }

}
