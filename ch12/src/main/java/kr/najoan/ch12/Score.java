package kr.najoan.ch12;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

@WebServlet("/score")
public class Score extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    public Score() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");

        request.getParameter("name");
        request.getParameter("kor");
        request.getParameter("eng");
        request.getParameter("math");

        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"utf-8\">");
        out.println("</head>");
        out.println("<body>");
        out.printf("이름: %s, 국어: %s, 영어: %s, 수학: %s",request.getParameter("name"), request.getParameter("kor"), request.getParameter("eng"), request.getParameter("math"));
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }
}
