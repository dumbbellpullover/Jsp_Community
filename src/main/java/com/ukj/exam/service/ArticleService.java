package com.ukj.exam.service;

import com.ukj.exam.dao.ArticleDao;
import com.ukj.exam.dto.Article;
import com.ukj.exam.dto.ResultData;
import com.ukj.exam.util.Util;

import java.sql.Connection;
import java.util.List;

public class ArticleService {
  private ArticleDao articleDao;
  public ArticleService(Connection conn) {
    this.articleDao = new ArticleDao(conn);
  }

  public int getItemsInAPage() {
    return 5;
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();

    int totalPage = (int) Math.ceil( (double) articleDao.getTotalCount() / itemInAPage);

    return totalPage;
  }

  public List<Article> getForPrintArticles(int page) {
    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    List<Article> articles = articleDao.getArticles(itemInAPage, limitFrom);
    return articles;
  }

  public ResultData write(String title, String body, int loggedMemberId) {
    int id = articleDao.write(title, body, loggedMemberId);

    return ResultData.from("S-1", Util.f("%d번 게시물이 생성되었습니다.", id), "id", id);
  }
}
