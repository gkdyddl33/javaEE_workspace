/*
 	이 클래스는 하위 컨트롤러로서 역할을 수행해야 하므로, 반드시 Controller 인터페이스를 구현해야 한다.
*/
package com.model2.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.controller.Controller;

public class TestController implements Controller{
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 3~4단계 : 일을 시키고..저장
		String msg = "test";
		
		HttpSession session = request.getSession();
		session.setAttribute("result", msg);
		
	}

	public String getResultView() {
		return "/test/result.jsp";	// 외부데이터환경 - 파일명이 하드코딩되어있다.
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}
}
