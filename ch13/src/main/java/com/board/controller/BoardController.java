package com.board.controller;

import com.board.dao.BoardDao;
import com.board.dto.BoardDto;
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
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String com = uri.substring(contextPath.length());

        String view = null;
        switch (com) {
            case "/", "/list" -> {
                request.setAttribute("msgList", BoardDao.getInstance().selectList());
                view = "list.jsp";
            }
            case "/view" -> {
                int num = Integer.parseInt(request.getParameter("num"));
                BoardDto dto = BoardDao.getInstance().selectOne(num, true);

                dto.setTitle(dto.getTitle().replace(" ", "&nbsp;"));
                dto.setContent(dto.getContent().replace(" ", "&nbsp;").replace("\n", "<br>"));

                request.setAttribute("msg", dto);
                view = "view.jsp";
            }
            case "/write" -> {
                String numStr = request.getParameter("num");
                int num = numStr == null ? 0 : Integer.parseInt(numStr);

                BoardDto dto = new BoardDto();
                String action = "insert";

                if (num > 0) {
                    dto = BoardDao.getInstance().selectOne(num, false);
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
                    Class.forName("org.mariadb.jdbc.Driver");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if (writer != null && !writer.isEmpty() &&
                        title != null && !title.isEmpty() &&
                        content != null && !content.isEmpty()) {

                    BoardDto dto = new BoardDto();
                    dto.setWriter(writer);
                    dto.setTitle(title);
                    dto.setContent(content);
                    BoardDao.getInstance().insertOne(dto);

                    view = "redirect:list";
                } else {
                    request.setAttribute("errorMessage", "모든 항목이 빈칸 없이 입력되어야 합니다.");
                    view = "errorBack.jsp";
                }
            }
            case "/update" -> {
                int num = Integer.parseInt(request.getParameter("num"));

                String writer = request.getParameter("writer");
                String title = request.getParameter("title");
                String content = request.getParameter("content");

                if (writer != null && !writer.isEmpty() &&
                        title != null && !title.isEmpty() &&
                        content != null && !content.isEmpty()) {

                    BoardDto dto = new BoardDto();
                    dto.setNum(num);
                    dto.setWriter(writer);
                    dto.setTitle(title);
                    dto.setContent(content);
                    BoardDao.getInstance().updateOne(dto);

                    view = "redirect:view?num=" + num;
                } else {
                    request.setAttribute("errorMessage", "모든 항목이 빈칸 없이 입력되어야 합니다.");
                    view = "errorBack.jsp";
                }
            }
            case "/delete" -> {
                int num = Integer.parseInt(request.getParameter("num"));
                BoardDao.getInstance().deleteOne(num);

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
