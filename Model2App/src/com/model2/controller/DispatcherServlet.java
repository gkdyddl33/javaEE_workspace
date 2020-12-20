/*
	�� ���� ��� Ŭ���̾�Ʈ�� ��û�� �ް�, ������ �����ϴ� ��Ʈ�ѷ� ����
	1. ��û�� �޴´�.(DispathcherServlet)
	2. ��û�� �м��ϰ� �����Ѵ�.(DispathcherServlet)
	3. �������� �˸´� ���� ��Ų��.(���� ��Ʈ�ѷ�)
	4. ����� �����Ѵ�.(���� ��Ʈ�ѷ�)
	5. Ŭ���̾�Ʈ���� �˸´� ��� ��(������)�� �����ش�. 
*/
package com.model2.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DispatcherServlet extends HttpServlet{
	FileReader fis;	// ��Ʈ�ѷ� ���� ���������� �б� ���� ��Ʈ��
	JSONObject controllerMap;	// ��Ʈ�ѷ��� �������� ����ִ� ��
	JSONObject 	viewMap;	// ��Ʈ�ѷ��� �������� ����ִ� ��
	
	public void init(ServletConfig config) throws ServletException {
		String contextConfigLocation = config.getInitParameter("contextConfigLocation");
		String realPath = config.getServletContext().getRealPath(contextConfigLocation);
		System.out.println(realPath);
		
		try {			
			fis = new FileReader(realPath);
			JSONParser jsonParser = new JSONParser();
			
			// 2�ܰ� : �Ľ� = �м�
			JSONObject json = (JSONObject)jsonParser.parse(fis);
			// ������ ����..
			controllerMap = (JSONObject)json.get("controller");	
			viewMap = (JSONObject)json.get("view");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}				
	}
	
	// 1�ܰ� : ��û�� �޴´� = doXXX
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}
	
	// 2�ܰ� : ��û�� �м��ϰ� �����Ѵ�.
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();	//��û�� ���а�!
		
		// if���� ����� ����ȭ�� �����͸� ��������(xml,json,properties)
		String controllerName = (String)controllerMap.get(uri);	
		System.out.println("���� ���� ��û�� ó���� ��Ʈ�ѷ� Ŭ������ "+controllerName);
	
		Class controllerClass=null;
		Controller controller=null;
		// ���� ���� ��Ʈ�ѷ��� �̸��� �˾�����, �ν��Ͻ��� �����, execute(),getResultView() ȣ��
		try {
			controllerClass = Class.forName(controllerName);	// ���ڿ��� ������ Ŭ������ ���� Ŭ���� ��ȯ
			controller = (Controller)controllerClass.newInstance();	// �ν��Ͻ� ����
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}	
		controller.execute(request, response); 	// 3�ܰ� ���� ������ ��������
		// 5�ܰ� : Ŭ���̾�Ʈ���� �˸´� �並 ��������.
		String resultKey = controller.getResultView();
		System.out.println(resultKey);
		String viewPage = (String)viewMap.get(resultKey);	// jsp ��Ī��ȯ
				
		// ���� ��Ʈ�ѷ��κ��� �Ѱܹ��� Ű ���� �̿��Ͽ� ���� �������� �˻��ϰ�, �� ����� �̿��Ͽ�
		// Ŭ���̾�Ʈ�� ������ �� �������� �Ѱ�����.
		// ����� sendRedirect�� ó���ؾ� �� ��찡 �ְ�, ���ۼ� �� ����Ʈ, ���� �ٸ� �������� �������� �õ��ϰ� �Ҷ�..
		if(controller.isForward()) {
			// ���δ� forwarding���� ó���ؾ� �� ��찡 �ִ�. �����͸� �����ϰ� ���� ��..�������� ����ϰ� ���� ��..
			RequestDispatcher dis = request.getRequestDispatcher(viewPage);
			dis.forward(request, response);
		}else {// ���������.
			response.sendRedirect(viewPage);	// �������� ���Ӱ� �õ��� ���
		}
	}
		
	public void destroy() {
		if(fis != null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
