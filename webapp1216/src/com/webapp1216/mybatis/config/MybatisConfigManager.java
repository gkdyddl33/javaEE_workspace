/*
 	mybatis�� config.xml �� xml. �ϻ� ���� �������� �ڹ� ���ø����̼ǰ��� ������� �����̴�.
 	���� �ڹ��ڵ忡�� config.xml�� �о�鿩�� �Ѵ�..
 	������ǥ : xml �� �鿩��, ���� �������� �������ִ� ��ü�� SqlSession ��� ����
 	
 	 �� Ŭ������ Ư�� new �Ҷ����� �ν��Ͻ��� ������ ���̰�, �׷��� �Ǹ� sqlsession�� �ټ��� 
 	 �޸� ������ �ǹǷ� �װ� ������� �Ѵ�. �ϳ��� �����ؼ� ����ϰԲ�..
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
