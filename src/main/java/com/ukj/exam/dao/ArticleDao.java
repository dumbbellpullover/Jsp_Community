package com.ukj.exam.dao;

import com.ukj.exam.dto.Article;
import com.ukj.exam.util.DBUtil;
import com.ukj.exam.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
  private Connection conn;
  public ArticleDao(Connection conn) {
    this.conn = conn;
  }

  public int getTotalCount() {
    SecSql sql = SecSql.from("SELECT COUNT(*)");
    sql.append("FROM article");

    return DBUtil.selectRowIntValue(conn, sql);
  }

  public List<Article> getArticles(int itemInAPage, int limitFrom) {
    SecSql sql = SecSql.from("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Article> articles = new ArrayList<>();
    List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

    for (Map<String, Object> articleRow : articleRows) {
      articles.add(new Article(articleRow));
    }

    return articles;
  }
}
