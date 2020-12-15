<%@page import="admin.member.Admin"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%
	// 원래는 db 연동을 해서 조회해야하지만, String으로 비교를 먼저 해보자.
	String admin_id = "scott";
	String admin_pass = "1234";
	
	String mid = request.getParameter("mid");
	String password = request.getParameter("password");
	
	// 아이디가 같고, 비밀번호가 같다면
	if(mid.equals(admin_id)&&password.equals(admin_pass)){
		//로그인 성공
		Admin admin = new Admin();
		admin.setMid(mid);
		admin.setPassword(password);
		response.sendRedirect("/admin/index.jsp?admin="+admin);
	}else{
		// 로그인 실패
		out.print("로그인 정보가 올바르지 않습니다.");
	}
%>