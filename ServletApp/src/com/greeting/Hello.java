package com.greeting;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class Hello extends HttpServlet{// ������ Ŭ����
		
	public void init(ServletConfig config) throws ServletException {
		String msg = config.getInitParameter("msg");
		System.out.println("init() ȣ��� �Ѱܹ��� �Ķ���� ������ "+msg);
			
	}
	
	protected void service(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException ,java.io.IOException {
		String id = request.getParameter("id");
		
		// Ŭ���̾�Ʈ�� ����
		response.setContentType("text/html;charset=utf-8");	//������ �� ���ڵ�ó��
		PrintWriter out = response.getWriter();
		out.print("����� ������ �Ķ���ʹ� "+id);		
	}
		
	public void destroy() {
		System.out.println("�� �׾��");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("hello servlet do!!");
	}
}
