package com.ukj.exam.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/home/main")
public class HomeMainServlet extends HttpServlet{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset-utf-8");

    HttpSession session = req.getSession();
    boolean isLogged = false;
    int loggedMemberId = -1;

    if( session.getAttribute("loggedMemberId") != null ) {
      loggedMemberId = (int) session.getAttribute("loggedMemberId");
      isLogged = true;
    }

    req.setAttribute("isLogged", isLogged);
    req.setAttribute("loggedMemberId", loggedMemberId);

    req.getRequestDispatcher("../home/main.jsp").forward(req, resp);
    //main.jsp에게 age=22 정보를 넘겨줘, main.jsp에서 쓸 수 있음
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
