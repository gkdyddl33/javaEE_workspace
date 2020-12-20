package com.model2.controller;

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

public class Dispatcher extends HttpServlet{
	FileReader fis;
	JSONObject controllerMap;	// ��Ʈ�ѷ��� �������� ����ִ� ��
	JSONObject viewMap;	// ��Ʈ�ѷ��� �������� ����ִ� ��
	
	public void init(ServletConfig config) throws ServletException {
		String contextConfigLocation = config.getInitParameter("contextConfigLocation");	// json.mapping
		String realPath = config.getServletContext().getRealPath(contextConfigLocation);
		
		try {
			fis = new FileReader(realPath);	// ��� - json - �Ľ� �м�..
			JSONParser jsonParser = new JSONParser();
			
			JSONObject json = (JSONObject) jsonParser.parse(fis);		// mapping.json
			// ---------- mapping.json �ȿ� �ִ�  key �� value...
			controllerMap = (JSONObject) json.get("controller");		// json�� controller key..			
			viewMap = (JSONObject) json.get("view");	// json �� view key..
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	// 1�ܰ� : ��û�� �޴´�..
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request,response);
	}
	private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 2�ܰ� : ��û�� �м��Ѵ�.
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();	// ��û �м�..�ּҰ�..
		
		// if�� ����� ����ȭ�� �����͸� ��������.(xml,<json>,properties)
		String  controllerName = (String) controllerMap.get(uri);	// ��û..json�� controller key..
		Class controllerClass = null;
		Controller controller = null;
		
		try {
			controllerClass = Class.forName(controllerName);	// Ŭ������ Ŭ���� ��ȯ..
			controller = (Controller)controllerClass.newInstance();		// �ν��Ͻ�ȭ..
						
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		controller.execute(request, response);	// �ν��Ͻ�ȭ �Ѱſ� ��������..
		
		// �������ܰ�..
		// Ŭ���̾�Ʈ���� �˸´� �並 ��������.
		String resultKey = controller.getResultView();	// ������ ������ �޼ҵ带 ����..
		String viewPage = (String) viewMap.get(resultKey);	// ������ �޼ҵ带 ��������..
		
		// ���� �ڵ带 ������ ���� ������ �������� ���缭 �Ѱ��ְ� �ϵ��ڵ� ���� ���� ����..
		if(controller.isForward()) {
			RequestDispatcher dis = request.getRequestDispatcher(viewPage);
			dis.forward(request, response); 		// forword..ex)�󼼺��ⰰ�� ������ ������..
		}else {
			response.sendRedirect(viewPage); 		// �������� �õ��� ���..
		}
	}


	public void destroy() {
		if(fis !=null) {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
