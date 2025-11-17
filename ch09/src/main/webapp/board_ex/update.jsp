<%@ page import="java.sql.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %><%--
  Created by IntelliJ IDEA.
  User: 412-16
  Date: 2025-10-13
  Time: 오후 3:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    int num = Integer.parseInt(request.getParameter("num"));

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
        try (
                Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jspdb", "jsp", "1234");
                PreparedStatement ps = conn.prepareStatement(
                            "update board set writer=?, title=?, content=?, regtime=? where num=?" // sql injection 방지
                )
        ) {
            String curTime = LocalDate.now() + " " + LocalTime.now().toString().substring(0, 8);
            ps.setString(1, writer);
            ps.setString(2, title);
            ps.setString(3, content);
            ps.setString(4, curTime);
            ps.setInt(5, num);
            ps.executeUpdate();

        } catch (Exception e) {
            out.println(e.getMessage());
        }
        response.sendRedirect("view.jsp?num=" + num);
        return;
    }
%>

<!-- 에러 출력 -->
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>error</title>
</head>
<body>
    <script>
        alert('모든 항목이 빈칸 없이 입력되어야 합니다.');
        history.back();
    </script>
</body>
</html>
</body>
</html>
