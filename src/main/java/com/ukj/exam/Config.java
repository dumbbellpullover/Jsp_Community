package com.ukj.exam;

public class Config {

  String driverName = "com.mysql.cj.jdbc.Driver";
  public static String getDBUrl() {
    return "jdbc:mysql://127.0.0.1:3306/jsp_community?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
  }

  public static String getDBId() {
    return "guest";
  }

  public static String getDBPw() {
    return "bemyguest";
  }

  public static String getDriverClassName() {
    return "com.mysql.cj.jdbc.Driver";
  }


}
