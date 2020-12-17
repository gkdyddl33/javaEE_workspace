/*
 	자바에서 클래서의 인스턴스를 생성하는 방법에는 new 연산자만 있는 것은 아니다!!
 	
*/
package study;

import java.lang.reflect.Method;

public class InstanceTest {
	public static void main(String[] args) {
		try {
			Class dogClass = Class.forName("study.Dog");
			System.out.println("로드성공");
			Method[] methods = dogClass.getMethods();
			for(Method m : methods) {
				System.out.println(m.getName());
			}
			
			// 클래스를 new 로 인스턴스 시키지 않고 올리는 법
			Dog dog = (Dog)dogClass.newInstance();
			System.out.println(dog.getName());
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("로드실패");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
