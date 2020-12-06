<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="db.DBManager"%>
<%@ page import="java.sql.*"%>
<%@ include file="/inc/lib.jsp"%>
<%
	request.setCharacterEncoding("utf-8");
	String author = request.getParameter("author");
	String title = request.getParameter("title");
	String content = request.getParameter("content");
	String notice_id = request.getParameter("notice_id");

	String sql = "update notice set author='"+author+"', title='"+title+"',";
	sql += " where notice_id="+notice_id;

	DBManager dbManager = new DBManager();
	Connection con = null;
	PreparedStatement pstmt = null;

	con = dbManager.getConnection();
	pstmt = con.prepareStatement(sql);
	int result = pstmt.executeUpdate();

	if(result==0){
		out.print(getMsgBack("수정실패"));
	}else{
		out.print(getMsgURL("수정성공","/board/detail2.jsp?notice_id="+notice_id));
	}
	dbManager.release(con,pstmt);
%>