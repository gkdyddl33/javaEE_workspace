/*
 	모든 하위 컨트롤러가 반드시 구현해야 할 메서드를 정의한다! 	
*/
package com.model2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
	// 알맞는 비즈니스 객체에 일 시키기
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
	// 결과페이지 - 하위컨트롤러들이 업무를 하도록..
	public String getResultView();
	
	// 요청을 끊어야 할지, 유지해야 할지를 결정하는 메서드
	public boolean isForward();
}
