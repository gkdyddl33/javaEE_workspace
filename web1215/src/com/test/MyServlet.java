/*
 	오직 javaEE 기반의 웹 서버에서만 실행될 수 있는 클래스를 가리켜 서블릿이라 한다.
 	HttpServlet 을 상속받는 순간부터 웹 서버에서 실행될 수 있는 클래스인 서블릿으로 된다.
*/
package com.test;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyServlet extends HttpServlet{
	
	/* 클라이언트가 get방식으로 요청을 할 때, 그 요청을 처리하는 메서드이다.
	  	HttpServlet 의 메서드로 부터 상속받았다.. 하지만 그 내용은 우리가 오버라이드해서
	  	요청을 처리하자!
	  	1. 클라이언트에 문자열 전송	-> response	  -> 클래스로 브라우저에 호출이 안되서 간접적으로 호출해야됨	
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 결론 : 서블릿을 이용하여 html 적용하려면 문자열로 처리해야 함..
		out.print("<table width=\"100%\" border=\"1px\">");
		out.print("<tr>");
		out.print("<td>No</td>");
		out.print("<td>이미지</td>");
		out.print("<td>제목</td>");
		out.print("<td>작성자</td>");
		out.print("<td>등록일</td>");		
		out.print("<td>조회수</td>");		
		out.print("</tr>");
		out.print("</table>");
	}
}
