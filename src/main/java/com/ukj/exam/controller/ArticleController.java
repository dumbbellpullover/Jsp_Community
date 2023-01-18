package com.ukj.exam.controller;

import com.ukj.exam.Rq;
import com.ukj.exam.dto.Article;
import com.ukj.exam.service.ArticleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.Connection;
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

    rq.appendBody(articles.toString());

//------------------------------------------------------
  }
}
