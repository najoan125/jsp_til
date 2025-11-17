package kr.najoan.ch12;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.Serial;

@WebServlet("/MoveTest")
public class MoveTest extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public MoveTest() {
        super();
    }

    public void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String com = uri.substring(contextPath.length());

        String view = switch (com) {
            case "/", "/a" -> "a.jsp";
            case "/b" -> "redirect:b.jsp";
            default -> null;
        };

        if (view == null) {
            response.getWriter().println("Invalid URL");
        } else if (view.startsWith("redirect:")) {
            response.sendRedirect(view.substring(9));
        } else {
            request.getRequestDispatcher(view).forward(request, response);
        }
    }

    public void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
