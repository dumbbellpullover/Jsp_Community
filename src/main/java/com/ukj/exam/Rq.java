package com.ukj.exam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Rq {
  private final HttpServletRequest req;
  private final HttpServletResponse resp;
  private boolean isInvalid;
  private String controllerName;
  private String actionMethodName;

  public Rq(HttpServletRequest req, HttpServletResponse resp) {
    // 브라우저에서 들어오는 파라미터 UTF-8로 해석
    try {
      req.setCharacterEncoding("UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    // 서블릿이 HTML 파일 만들 때 UTF-8로 쓰기
    resp.setCharacterEncoding("UTF-8");

    // HTML이 UTF-8 형식이라는 것을 브라우저에게 알림
    resp.setContentType("text/html; charset-utf-8");

    this.req = req;
    this.resp = resp;

    String requestUri = req.getRequestURI();
    // localhost:8081/s/article/list
    String[] requestUriBits = requestUri.split("/");

    int minBitsCount = 4;

    if (requestUriBits.length < minBitsCount) {
      isInvalid = true;
      appendBody("올바른 요청이 아닙니다.");
      return;
    }

    this.controllerName = requestUriBits[2];
    this.actionMethodName = requestUriBits[3];
  }

  public HttpServletRequest getReq() {
    return req;
  }

  public String getControllerName() {
    return controllerName;
  }

  public String getActionMethodName() {
    return actionMethodName;
  }

  public boolean getIsInvalid() {
    return isInvalid;
  }

  public int getIntParam(String paramName, int defaultValue) {
    String value = req.getParameter(paramName);

    if (value == null) {
      return defaultValue;
    }

    try {
      return Integer.parseInt(value);

    } catch (NumberFormatException e) {
      return defaultValue;
    }

  }

  public void appendBody(String str) {
    try {
      resp.getWriter().append(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    }
}
