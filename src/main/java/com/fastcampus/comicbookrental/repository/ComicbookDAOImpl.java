package com.fastcampus.comicbookrental.repository;

import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class ComicbookDAOImpl implements ComicbookDAO {

    //root-context에 등록한 SqlSessiontemplate 의존성 주입
    @Autowired
    SqlSession session;

    //mapper 네임스페이스 지정 sql id을 뒤에 추가하기 위해 마지막에 '.'
    String namespace="com.fastcampus.comicbookrental.dao.ComicbookMapper.";

    //서비스 계층으로 예외 선언
    @Override
    public ComicbookDTO select(Integer cno) throws Exception{
        return session.selectOne(namespace+"select", cno);
    }

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    } // T selectOne(String statement)

    @Override
    public int deleteAll() {
        return session.delete(namespace+"deleteAll");
    } // int delete(String statement)

    @Override
    //삭제시에는 cno가 일치하고, 관리자인 경우에만
    public int delete(Integer cno, String isAdmin) throws Exception {
//        if(!isAdmin.equals("true")) throw new Exception();
        return session.delete(namespace+"delete", cno);
    } // int delete(String statement, Object parameter)

    @Override
    public int insert(ComicbookDTO dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    } // int insert(String statement, Object parameter)

    @Override
    public List<ComicbookDTO> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    } // List<E> selectList(String statement)


    @Override
    public List<ComicbookDTO> selectPage(Map map) throws Exception {
        return session.selectList(namespace+"selectPage", map);
    } // List<E> selectList(String statement, Object parameter)

    @Override
//    public int update(ComicbookDTO dto,String isAdmin) throws Exception {
    public int update(ComicbookDTO dto) throws Exception {
        return session.update(namespace+"update", dto);
    } // int update(String statement, Object parameter)

    @Override
    public int increaseViewCnt(Integer cno) throws Exception {
        return session.update(namespace+"increaseViewCnt", cno);
    } // int update(String statement, Object parameter)

    @Override
    public int searchResultCnt(SearchCondition sc) throws Exception {
        System.out.println("sc in searchResultCnt() = " + sc);
        System.out.println("session = " + session);
        System.out.println("sc = " + sc);
        return session.selectOne(namespace+"searchResultCnt", sc);
    } // T selectOne(String statement, Object parameter)

    @Override
    public List<ComicbookDTO> searchSelectPage(SearchCondition sc) throws Exception {
        return session.selectList(namespace+"searchSelectPage", sc);
    } // List<E> selectList(String statement, Object parameter)

}
