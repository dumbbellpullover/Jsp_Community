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

  public List<Article> getArticlesWithName(int itemInAPage, int limitFrom) {
    SecSql sql = SecSql.from("SELECT A.*, M.name");
    sql.append("FROM article AS A");
    sql.append("JOIN member AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Article> articles = new ArrayList<>();
    List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

    for (Map<String, Object> articleRow : articleRows) {
      articles.add(new Article(articleRow));
    }

    return articles;
  }

  public int write(String title, String body, int loggedMemberId) {

    SecSql sql = SecSql.from("INSERT INTO article");
    sql.append("(regDate, updateDate, memberId, title, body)");
    sql.append("VALUES");
    sql.append("(NOW(), NOW(), ?, ?, ?)", loggedMemberId, title, body);

    //CONCAT('title__', RAND())

    int id = DBUtil.insert(conn, sql);
    return id;
  }

  public Article getForPrintArticleById(int id) {

    SecSql sql = SecSql.from("SELECT *");
    sql.append("FROM article WHERE id = ?", id);

    return new Article(DBUtil.selectRow(conn, sql));
  }

  public Article getForPrintArticleByIdWithName(int id) {
    SecSql sql = SecSql.from("SELECT A.*, M.name");
    sql.append("FROM article AS A");
    sql.append("JOIN member AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("WHERE A.id = ?", id);
    sql.append("ORDER BY id DESC");

    return new Article(DBUtil.selectRow(conn, sql));
  }

  public void delete(int id) {
    SecSql sql = SecSql.from("DELETE ");
    sql.append("FROM article WHERE id = ?", id);

    DBUtil.delete(conn, sql);
  }

  public void modify(int id, String title, String body) {

    SecSql sql = SecSql.from("UPDATE article SET");
    sql.append("updateDate = NOW(), title = ?, body = ?", title, body);
    sql.append("WHERE id = ?", id);
    //CONCAT('title__', RAND())

    DBUtil.update(conn, sql);
  }
}
