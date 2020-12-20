package study;

import java.lang.reflect.Method;

public class Instance {
	public static void main(String[] args) {
		try {
			Class dogClass = Class.forName("study.Dog");
			System.out.println("로드성공");
			
			Method[] methods = dogClass.getMethods();
			for(Method m : methods) {
				System.out.println(m.getName());
			}
			
			// 클래스를 new로 인스턴스 시키지 않고 메모리에 올려보자.
			Dog dog = (Dog)dogClass.newInstance();
			System.out.println(dog.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
