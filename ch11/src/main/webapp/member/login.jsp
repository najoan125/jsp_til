<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%
    Class.forName("org.mariadb.jdbc.Driver");
    try (
            Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jspdb", "jsp", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(
                    "select * from member where id='%s' and pw='%s'",
                    request.getParameter("id"), request.getParameter("pw")))
    ) {
        if (rs.next()) {
            session.setAttribute("userId", rs.getString("id"));
            session.setAttribute("userName", rs.getString("name"));
//             response.sendRedirect("login_main.jsp");   // 로그인 메인 화면으로 돌아감
            out.println(
                    "<script>" +
                            "parent.location.reload()" +
                            "</script>"
            );
            return;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>

<script>
    alert('아이디 또는 비밀번호가 틀립니다!');
    history.back();
</script>

</body>
</html>
