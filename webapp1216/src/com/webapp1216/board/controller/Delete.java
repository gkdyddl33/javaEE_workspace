package com.webapp1216.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.webapp1216.board.model.NoticeDAO;

public class Delete extends HttpServlet{
	NoticeDAO noticeDAO = new NoticeDAO();
	
	// ����? get or post ����	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String notice_id = request.getParameter("notice_id");
		
		int result = noticeDAO.delete(Integer.parseInt("notice_id"));
		HttpSession session = request.getSession();
		if(result==0) {
			session.setAttribute("msg", "���� �������� �ʾҽ��ϴ�. �����ڿ��� �����ϼ���.");
			response.sendRedirect("/error/message.jsp");
		}else {
			response.sendRedirect("/board/list");
		}
	}
}