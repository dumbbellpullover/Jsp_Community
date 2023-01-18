package com.ukj.exam.dto;

import java.util.Map;

public class Article {
  public int id;
  public String regDate;
  public String updateDate;
  public int memberId;
  public String title;
  public String body;

  public Article() {

  }

  public Article(Map<String, Object> row) {
    this.id = (int) row.get("id");
    this.regDate = (String) row.get("regDate");
    this.updateDate = (String) row.get("updateDate");
    this.memberId = (int) row.get("memberId");
    this.title = (String) row.get("title");
    this.body = (String) row.get("body");
  }
}
