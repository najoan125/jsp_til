package com.board.controller;

import com.board.dto.BoardDto;
import com.board.service.BoardService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/")
public class BoardController extends HttpServlet {
    public BoardController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardService service = new BoardService();
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String com = uri.substring(contextPath.length());

        String view = null;
        switch (com) {
            case "/", "/list" -> {
                String tmp = request.getParameter("page");
                int pageNum = (tmp == null || tmp.isEmpty()) ? 1 : Integer.parseInt(tmp);
                request.setAttribute("msgList", service.getMsgList(pageNum));

                view = "list.jsp";
            }
            case "/view" -> {
                int num = Integer.parseInt(request.getParameter("num"));
                request.setAttribute("msg", service.getMsg(num));

                view = "view.jsp";
            }
            case "/write" -> {
                String numStr = request.getParameter("num");
                int num = numStr == null ? 0 : Integer.parseInt(numStr);

                BoardDto dto = new BoardDto();
                String action = "insert";

                if (num > 0) {
                    dto = service.getMsgForWrite(num);
                    action = "update?num=" + num;
                }

                request.setAttribute("msg", dto);
                request.setAttribute("action", action);
                view = "write.jsp";
            }
            case "/insert" -> {
                String writer = request.getParameter("writer");
                String title = request.getParameter("title");
                String content = request.getParameter("content");

                try {
                    service.writeMsg(writer, title, content);
                    view = "redirect:list";
                } catch (Exception e) {
                    request.setAttribute("errorMessage", e.getMessage());
                    view = "errorBack.jsp";
                }
            }
            case "/update" -> {
                int num = Integer.parseInt(request.getParameter("num"));
                String writer = request.getParameter("writer");
                String title = request.getParameter("title");
                String content = request.getParameter("content");

                try {
                    service.updateMsg(num, writer, title, content);
                    view = "redirect:view?num=" + num;
                } catch (Exception e) {
                    request.setAttribute("errorMessage", e.getMessage());
                    view = "errorBack.jsp";
                }
            }
            case "/delete" -> {
                int num = Integer.parseInt(request.getParameter("num"));

                service.deleteMsg(num);
                view = "redirect:list";
            }
            default -> response.getWriter().println("Invalid URL");
        }

        if (view == null) {
            response.getWriter().println("Invalid URL");
        } else if (view.startsWith("redirect:")) {
            response.sendRedirect(view.substring(9));
        } else {
            request.getRequestDispatcher(view).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
