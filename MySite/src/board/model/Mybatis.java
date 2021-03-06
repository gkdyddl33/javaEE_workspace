package board.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import mybatis.config.MybatisConfigManager;

public class Mybatis {
	// dao를 사용하기 위해서는 mapper 호출 및 드라이버 생성
	MybatisConfigManager configManager = MybatisConfigManager.getInstance();
	
	public int insert(Board board) {
		int result = 0;
		SqlSession sqlSession = configManager.getSqlSession();	// 반환
		result = sqlSession.insert("Board.insert", board);
		sqlSession.commit();
		configManager.close(sqlSession);
		return result;
	}
	
	public List selectAll() {
		List list = null;
		SqlSession sqlSession = configManager.getSqlSession();	// 반환
		list = sqlSession.selectList("Board.selectAll");
		configManager.close(sqlSession);
		return list;
	}
	
	public Board select(int board_id) {
		Board board = null;
		SqlSession sqlSession = configManager.getSqlSession();	// 반환
		board = sqlSession.selectOne("Board.select", board_id);
		configManager.close(sqlSession);
		return board;
	}
	
	public int update(Board board) {
		int result = 0;
		SqlSession sqlSession = configManager.getSqlSession();	// 반환
		result = sqlSession.update("Board.update", board);
		sqlSession.commit();
		configManager.close(sqlSession);
		return result;
	}
	
	public int delete(int board_id) {
		int result = 0;
		SqlSession sqlSession = configManager.getSqlSession();	// 반환
		result = sqlSession.delete("Board.delete", board_id);
		sqlSession.commit();
		configManager.close(sqlSession);
		return result;
	}
}
