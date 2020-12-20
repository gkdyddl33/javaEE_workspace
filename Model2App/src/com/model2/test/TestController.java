/*
 	�� Ŭ������ ���� ��Ʈ�ѷ��μ� ������ �����ؾ� �ϹǷ�, �ݵ�� Controller �������̽��� �����ؾ� �Ѵ�.
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
		// 3~4�ܰ� : ���� ��Ű��..����
		String msg = "test";
		
		HttpSession session = request.getSession();
		session.setAttribute("result", msg);
		
	}

	public String getResultView() {
		return "/test/result.jsp";	// �ܺε�����ȯ�� - ���ϸ��� �ϵ��ڵ��Ǿ��ִ�.
	}

	@Override
	public boolean isForward() {
		// TODO Auto-generated method stub
		return false;
	}
}
