package com.ukj.exam.servlet;

import com.ukj.exam.Rq;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/gugudan")
public class GugudanServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Rq rq = new Rq(req, resp);

    int dan = rq.getIntParam("dan", 0);
    int limit = rq.getIntParam("limit", 0);

    // request에 정보를 담는다.
    // 왜냐하면 나중에 gugudan2.jsp.jsp에서 해당 내용을 꺼낼 수 있기 떄문에

    req.setAttribute("dan", dan);
    req.setAttribute("limit", limit);

    // guguda2.jsp 에게 나머지 작업을 토스
    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/gugudan2.jsp");
    requestDispatcher.forward(req, resp);

  }
}
