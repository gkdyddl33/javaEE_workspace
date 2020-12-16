package mybatis.config;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Mybatis {
	// ����̹� ��� ������ x -> �ѹ���
	private static Mybatis instance;
	
	String resource = "mybatis/config/config.xml";
	InputStream inputStream;
	SqlSessionFactory sqlSessionFactory;
	public Mybatis() {
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// �ν��Ͻ� ����
	public static Mybatis getInstance() {
		if(instance==null) {
			instance = new Mybatis();
		}
		return instance;
	}
	// sqlSession ��ȯ
	public SqlSession getSqlSession(SqlSession sqlSession) {
		SqlSession sqlSession2 = null;
		sqlSession2= sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	// sqlSession �ݳ�
	public void close(SqlSession sqlSession) {
		sqlSession.close();
	}
}