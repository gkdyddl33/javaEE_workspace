package mybatis.config;

import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Mybatis {
	// 드라이버 계속 생성은 x -> 한번만
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
	// 인스턴스 막음
	public static Mybatis getInstance() {
		if(instance==null) {
			instance = new Mybatis();
		}
		return instance;
	}
	// sqlSession 반환
	public SqlSession getSqlSession(SqlSession sqlSession) {
		SqlSession sqlSession2 = null;
		sqlSession2= sqlSessionFactory.openSession();
		return sqlSession;
	}
	
	// sqlSession 반납
	public void close(SqlSession sqlSession) {
		sqlSession.close();
	}
}
