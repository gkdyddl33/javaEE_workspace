package com.webapp1216.board.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.webapp1216.mybatis.config.MybatisConfigManager;

public class NoticeDAO {
	MybatisConfigManager configManager =MybatisConfigManager.getInstance();
	
	// CRUD
	public int insert(Notice notice) {
		int result=0;
		SqlSession sqlSession = configManager.getSqlSession();
		result = sqlSession.insert("Notice.insert", notice);
		sqlSession.commit();
		configManager.close(sqlSession);
		return result;
	}
	
	public List selectAll() {
		List list=null;
		SqlSession sqlSession  = configManager.getSqlSession();
		list=sqlSession.selectList("Notice.selectAll");
		configManager.close(sqlSession);
		return list;
	}
	
	public Notice select(int notice_id) {
		Notice notice = null;
		SqlSession sqlSession = configManager.getSqlSession();
		notice = sqlSession.selectOne("Notice.select", notice_id);
		configManager.close(sqlSession);
		return notice;
	}
	
	public int update(Notice  notice) {
		int result =0;
		SqlSession sqlSession = configManager.getSqlSession();
		result = sqlSession.delete("Notice.update",notice);
		sqlSession.commit();
		configManager.close(sqlSession);
		return result;
	}
	
	public int delete(int notice_id) {
		int result =0;
		SqlSession sqlSession = configManager.getSqlSession();
		result = sqlSession.delete("Notice.delete",notice_id);
		sqlSession.commit();
		configManager.close(sqlSession);
		return result;
	}
}
