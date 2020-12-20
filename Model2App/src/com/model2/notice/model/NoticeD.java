package com.model2.notice.model;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.model2.mybatis.config.Mybatis;
import com.model2.notice.domain.Notice;

public class NoticeD {
	Mybatis mybatis = Mybatis.getInstance();
	
	// CRUD
	public List selectAll() {
		List list = null;
		SqlSession sqlSession = mybatis.getSqlSession();
		list = sqlSession.selectList("Notice.selectAll");
		mybatis.close(sqlSession);
		return list;
	}
	
	public int insert(Notice notice) {
		int result = 0;
		SqlSession sqlSession = mybatis.getSqlSession();
		result = sqlSession.insert("Notice.insert", notice);
		sqlSession.commit();
		mybatis.close(sqlSession);
		return result;
	}
	
	public Notice select(int notice_id) {// 하나의 목록 가져오기 목록..vo에 넣어서..
		Notice notice = null;
		SqlSession sqlSession = mybatis.getSqlSession();
		notice = sqlSession.selectOne("Notice.select",notice_id);
		mybatis.close(sqlSession);
		return notice;
	}
}
