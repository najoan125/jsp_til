<%@ page import="java.sql.*" %><%--
  Created by IntelliJ IDEA.
  User: najoan
  Date: 2025. 10. 20.
  Time: 오후 3:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>delete</title>
</head>
<body>
    <%
        int num = Integer.parseInt(request.getParameter("num"));

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (
                Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jspdb", "jsp", "1234");
                PreparedStatement stmt = conn.prepareStatement("delete from board where num=?"); // sql injection 방지
        ) {
            stmt.setInt(1, num);
            stmt.executeUpdate();

            response.sendRedirect("list.jsp");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    %>
</body>
</html>
