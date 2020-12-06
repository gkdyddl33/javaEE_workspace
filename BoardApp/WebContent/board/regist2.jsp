<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%!
	String url = "jdbc:mariadb://localhost:3306/db1202";
	String user="root";
	String password="1234";
%>
<%
	request.setCharacterEncoding("utf-8");
	String author = request.getParameter("author");
	String title = request.getParameter("title");
	String content = request.getParameter("content");

	Class.forName("org.mariadb.jdbc.Driver");

	Connection con = null;
	PreparedStatement pstmt = null;

	con = DriverManager.getConnection(url,user,password);
	if(con==null){
%>
	<script>
		alert("접속실패");
		history.back();
	</script>
<%
	}else{
%>
	<script>
		alert("접속성공");
	</script>
<%
	String sql = "insert into notice(author,title,content) values(?,?,?)";
	pstmt = con.prepareStatement(sql);
	pstmt.setString(1,author);
	pstmt.setString(2,title);
	pstmt.setString(3,content);
	
	int result = pstmt.executeUpdate();
	if(result == 0){
%>
	<script>
		alert("등록실패");
		history.back();
	</script>
<%
			out.print("등록실패");
%>
<%
	}else{
%>
	<script>
		alert("등록성공");
		location.href="/board/list2.jsp";
	</script>
<%
		}
	}
	// db 연동에 사용된 모든 객체 닫기
	if(pstmt !=null){
		pstmt.close();
	}
	if(con != null){
		con.close();
	}
%>