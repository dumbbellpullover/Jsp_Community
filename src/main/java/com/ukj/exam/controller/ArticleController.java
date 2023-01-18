package com.ukj.exam.controller;

import com.ukj.exam.service.ArticleService;
import com.ukj.exam.util.DBUtil;
import com.ukj.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleController {
  private HttpServletRequest req;
  private HttpServletResponse resp;
  private ArticleService articleService;

  public ArticleController(HttpServletRequest req, HttpServletResponse resp, Connection conn) {
    this.req = req;
    this.resp = resp;

    this.articleService = new ArticleService(conn);
  }

  public void actionList() throws ServletException, IOException {
    //로직------------------------------------------------------

    int page = 1;
    if (req.getParameter("page") != null && req.getParameter("page").length() != 0) {
      page = Integer.parseInt(req.getParameter("page"));
    }

    int totalPage = articleService.getForPrintListTotalPage();
    List<Map<String, Object>> articleRows = articleService.getForPrintArticleRows(page);

    req.setAttribute("articleRows", articleRows);
    req.setAttribute("page", page);
    req.setAttribute("totalPage", totalPage);
    req.getRequestDispatcher("/jsp/article/list.jsp").forward(req, resp);

    resp.getWriter().append(articleRows.toString());

//------------------------------------------------------
  }
}
