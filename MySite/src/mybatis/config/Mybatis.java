package mybatis.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Mybatis {// 드라이버 느낌..
	private static Mybatis instance;	// 싱글턴
	
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
	// 인스턴스를 얻어갈 수 있게 별도로 제한해둠..
	public static Mybatis getInstance() {
		if(instance==null) {
			instance = new Mybatis();
		}
		return instance;
	}
	
	// 반환해주는 메서드
	public SqlSession getSqlSession(SqlSession sqlSession) {
		SqlSession sqlSession2 = null;
		sqlSession2 = sqlSessionFactory.openSession();		
		return sqlSession;
	}
	// 반납해주는 메서드
	public void close(SqlSession sqlSession) {
		sqlSession.close();
	}
}
