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
<title>Insert title here</title>
</head>
<body>
<%
	// JNDI - XML과 같은 자원으로 관리하는 방법 
	InitialContext initctx = new InitialContext();
	DataSource ds = (DataSource)initctx.lookup("java:comp/env/jdbc/oracle");	// 외부환경
	
	Connection con = ds.getConnection();
	Statement stmt = con.createStatement();
	ResultSet rset = stmt.executeQuery("select * from board");
	while(rset.next()){
		out.print("title=="+rset.getString("title")+"<br>");
	}
	rset.close();
	stmt.close();
	con.close();	// 여기서 풀로 돌려보낸다!
	initctx.close();
%>
</body>
</html>