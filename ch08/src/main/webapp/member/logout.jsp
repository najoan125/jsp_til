<%--
  Created by IntelliJ IDEA.
  User: najoan
  Date: 2025. 9. 29.
  Time: 오후 2:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    session.removeAttribute("userId");
    session.removeAttribute("userName");
    response.sendRedirect("login_main.jsp");
%>