package com.ukj.exam.service;

import com.ukj.exam.dao.ArticleDao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private ArticleDao articleDao;
  public ArticleService(Connection conn) {
    this.articleDao = new ArticleDao(conn);
  }

  public int getItemsInAPage() {
    return 20;
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();

    int totalPage = (int) Math.ceil( (double) articleDao.getTotalCount() / itemInAPage);

    return totalPage;
  }

  public List<Map<String, Object>> getForPrintArticleRows(int page) {
    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    List<Map<String, Object>> articleRows = articleDao.getArticleRows(itemInAPage, limitFrom);
    return articleRows;
  }
}
