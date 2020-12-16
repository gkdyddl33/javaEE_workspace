/*
 	mybatis의 config.xml 은 xml. 일뿐 현재 실행중인 자바 어플리케이션과는 상관없는 상태이다.
 	따라서 자바코드에서 config.xml을 읽어들여야 한다..
 	최종목표 : xml 을 들여서, 실제 쿼리문을 수행해주는 객체인 SqlSession 얻기 위함
 	
 	 이 클래스는 특히 new 할때마다 인스턴스가 생성될 것이고, 그렇게 되면 sqlsession이 다수가 
 	 메모리 생성이 되므로 그걸 막아줘야 한다. 하나만 생성해서 사용하게끔..
*/
package com.webapp1216.mybatis.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisConfigManager {
	private static MybatisConfigManager instance;
	
	String resource = "com/webapp1216/mybatis/config/config.xml";
	InputStream inputStream;
	SqlSessionFactory sqlSessionFactory;
	public MybatisConfigManager() {
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MybatisConfigManager getInstance() {
		if(instance==null) {
			instance=new MybatisConfigManager();
		}
		return instance;
	}
	public SqlSession getSqlSession() {
		SqlSession sqlSession = null;
		sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	public void close(SqlSession sqlSession) {
		sqlSession.close();
	}
}
