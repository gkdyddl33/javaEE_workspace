package study;

import java.lang.reflect.Method;

public class Instance {
	public static void main(String[] args) {
		try {
			Class dogClass = Class.forName("study.Dog");
			System.out.println("�ε强��");
			
			Method[] methods = dogClass.getMethods();
			for(Method m : methods) {
				System.out.println(m.getName());
			}
			
			// Ŭ������ new�� �ν��Ͻ� ��Ű�� �ʰ� �޸𸮿� �÷�����.
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
