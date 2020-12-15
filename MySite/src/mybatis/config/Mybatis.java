package mybatis.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Mybatis {// ����̹� ����..
	private static Mybatis instance;	// �̱���
	
	String resource = "mybatis/config/config.xml";
	InputStream inputStream;
	SqlSessionFactory sqlSessionFactory;
	
	public Mybatis() {
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// �ν��Ͻ��� �� �� �ְ� ������ �����ص�..
	public static Mybatis getInstance() {
		if(instance==null) {
			instance = new Mybatis();
		}
		return instance;
	}
	
	// ��ȯ���ִ� �޼���
	public SqlSession getSqlSession(SqlSession sqlSession) {
		SqlSession sqlSession2 = null;
		sqlSession2 = sqlSessionFactory.openSession();		
		return sqlSession;
	}
	// �ݳ����ִ� �޼���
	public void close(SqlSession sqlSession) {
		sqlSession.close();
	}
}
