package com.greeting;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Hello extends HttpServlet{// 웹서버 클래스
		
	public void init(ServletConfig config) throws ServletException {
		String msg = config.getInitParameter("msg");
		System.out.println("init() 호출시 넘겨받은 파라미터 정보는 "+msg);
			
	}
	
	protected void service(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException ,java.io.IOException {
		String id = request.getParameter("id");
		
		// 클라이언트에 전송
		response.setContentType("text/html;charset=utf-8");	//응답할 때 인코딩처리
		PrintWriter out = response.getWriter();
		out.print("당신이 전송한 파라미터는 "+id);		
	}
		
	public void destroy() {
		System.out.println("저 죽어요");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("hello servlet do!!");
	}
}
