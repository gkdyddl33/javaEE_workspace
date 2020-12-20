package com.model2.mybatis.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Mybatis {
	InputStream inputStream;
	SqlSessionFactory sqlSessionFactory;
	
	private static Mybatis instance;
	
	public Mybatis() {
		String resource = "com/model2/mybatis/config/config.xml";
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Mybatis getInstance() {
		if(instance==null) {
			instance = new Mybatis();
		}
		return instance;
	}
	// sqlÄõ¸® ¼öÇà °´Ã¼
	public SqlSession getSqlSession() {
		SqlSession sqlSession = null;
		sqlSession = sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	public void close(SqlSession sqlSeesion) {
		if(sqlSeesion != null) {
			sqlSeesion.close();
		}
	}
}
