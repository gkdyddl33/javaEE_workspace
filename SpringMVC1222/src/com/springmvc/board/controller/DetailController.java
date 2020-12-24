package com.springmvc.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.model2.board.model.BoardDAO;
import com.model2.domain.Board;

public class DetailController implements Controller{
	BoardDAO boardDAO = new BoardDAO();
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 3단계 : 일을 시킨다 = 상세보기 한건의 게시물
		request.setCharacterEncoding("utf-8");
		String board_id = request.getParameter("board_id");
		
		Board board = boardDAO.select(Integer.parseInt(board_id)); 
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("board", board); 
		mav.setViewName("/board/detail");  
		return mav;
	}

}
