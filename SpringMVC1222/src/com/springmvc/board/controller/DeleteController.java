package com.springmvc.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model2.board.model.BoardDAO;
import com.model2.domain.Board;

public class DeleteController implements Controller{
	BoardDAO boardDAO = new BoardDAO();
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 3�ܰ� : ���� ��Ų�� = �����ϱ� �Ѱ��� �Խù�..
		request.setCharacterEncoding("utf-8");
		String board_id = request.getParameter("board_id");
		
		int result =boardDAO.delete(Integer.parseInt(board_id));
	    
 		ModelAndView mav = new ModelAndView();
		mav.addObject("result", result);
		mav.setViewName("/board/delete"); 
		return mav;
	}

}
