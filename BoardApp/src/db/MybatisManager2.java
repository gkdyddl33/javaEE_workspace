package db;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisManager2 {

	private SqlSessionFactory sqlSessionFactory;
	
	public MybatisManager2() {
		String resource = "mybatis/config/config.xml";
		InputStream inputStream;
		
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public SqlSessionFactory getsqlSessionFactory() {
		return sqlSessionFactory;
	}
	
	public static void main(String[] args) {
		new MybatisManager2();
	}
	
}
