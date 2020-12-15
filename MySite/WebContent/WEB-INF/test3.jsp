<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>JDBC JNDI Resource Test</title>
<body>
<%
	InitialContext initCtx = new InitialContext();
	DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/myoracle");
	
	Connection con = ds.getConnection();
	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery("select * from board");
	
	while(rs.next()){
		out.println("title=="+rs.getString("title")+"<br>");
	}
	rs.close();
	stmt.close();
	con.close();
	initCtx.close();
%>
</body>
</html>