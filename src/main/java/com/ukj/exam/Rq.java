package com.ukj.exam;

import com.ukj.exam.util.Util;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@ToString
public class Rq {
  private final HttpServletRequest req;
  private final HttpServletResponse resp;
  @Getter
  private boolean isInvalid;
  @Getter
  private String controllerTypeName;
  @Getter
  private String controllerName;
  @Getter
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
      print("올바른 요청이 아닙니다.");
      return;
    }

    this.controllerTypeName = requestUriBits[1];
    this.controllerName = requestUriBits[2];
    this.actionMethodName = requestUriBits[3];
  }

  public String getParam(String paramName, String defaultValue) {
    String value = req.getParameter(paramName);

    if (value == null) {
      return defaultValue;
    }

    try {
      return value;

    } catch (NullPointerException e) {
      return defaultValue;
    }

  }

  public int getIntParam(String paramName, int defaultValue) {
    String value = req.getParameter(paramName);

    if (value == null) {
      System.out.println(1);
      return defaultValue;
    }

    try {
      System.out.println(2);
      return Integer.parseInt(value);

    } catch (NumberFormatException e) {
      return defaultValue;
    }

  }

  public void print(String str) {
    try {
      resp.getWriter().append(str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  public void println(String str) {
    print(str + "\n");
  }

  public void printf(String format, Object... args) {
    print(Util.f(format, args));
  }

  public void jsp (String jspPath) {
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/jsp" + jspPath + ".jsp");

    try {
      requestDispatcher.forward(req, resp);
    } catch (ServletException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void setAttr(String attrName, Object attrValue) {
    req.setAttribute(attrName, attrValue);
  }

  public void historyBack(String msg) {
    println("<script>");
    printf("alert('%s');\n", msg);
    println("history.back();");
    println("</script>");
  }

  public void replace(String msg, String redirectUri) {
    println("<script>");
    printf("alert('%s');\n", msg);
    printf("location.replace('%s');\n", redirectUri);
    println("</script>");
  }

}
