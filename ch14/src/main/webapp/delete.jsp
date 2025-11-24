<%@ page import="com.board.dao.BoardDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>delete</title>
</head>
<body>
    <%
        int num = Integer.parseInt(request.getParameter("num"));
        BoardDao.getInstance().deleteOne(num);

        response.sendRedirect("list");
    %>
</body>
</html>
