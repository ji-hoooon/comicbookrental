package com.fastcampus.comicbookrental.repository;

import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.RentalDTO;
import com.fastcampus.comicbookrental.dto.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RentalDAOImpl implements RentalDAO {
    //root-context에 등록한 SqlSessiontemplate 의존성 주입
    @Autowired
    SqlSession session;

    //mapper 네임스페이스 지정 sql id을 뒤에 추가하기 위해 마지막에 '.'
    String namespace="com.fastcampus.comicbookrental.repository.RentalMapper.";

    //서비스 계층으로 예외 선언
    @Override
    public RentalDTO select(Integer rno) throws Exception{
        return session.selectOne(namespace+"select", rno);
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
    //삭제시에는 rno와 id가 일치하거나 관리자인 경우에만
    public int delete(RentalDTO dto) throws Exception {
        return session.delete(namespace+"delete", dto);
    } // int delete(String statement, Object parameter)

    @Override
    public int insert(ComicbookDTO dto, String id) throws Exception {
        Map map = new HashMap();
        map.put("cno", dto.getCno());
        map.put("id", id);
        return session.insert(namespace+"insert", map);
    } // int insert(String statement, Object parameter)

    @Override
    public List<RentalDTO> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    } // List<E> selectList(String statement)


    @Override
    public List<RentalDTO> selectPage(Map map) throws Exception {
        return session.selectList(namespace+"selectPage", map);
    } // List<E> selectList(String statement, Object parameter)

    @Override
//    public int update(RentalDTO dto,String isAdmin) throws Exception {
    public int update(RentalDTO dto) throws Exception {
        return session.update(namespace+"update", dto);
    } // int update(String statement, Object parameter)


    @Override
    public int searchResultCnt(SearchCondition sc) throws Exception {
        System.out.println("sc in searchResultCnt() = " + sc);
        System.out.println("session = " + session);
        System.out.println("sc = " + sc);
        return session.selectOne(namespace+"searchResultCnt", sc);
    } // T selectOne(String statement, Object parameter)

    @Override
    public List<RentalDTO> searchSelectPage(SearchCondition sc) throws Exception {
        return session.selectList(namespace+"searchSelectPage", sc);
    } // List<E> selectList(String statement, Object parameter)


    @Override
    public int addRentalHistory(RentalDTO dto){
        return session.insert(namespace+"insertRentalHistory", dto);
    }

    @Override
    public int modifyRentalHistory(RentalDTO dto){
        return session.update(namespace+"updateRentalHistory", dto);
    }

    @Override
    public List<RentalDTO> selectAllWithId(String id){
        return session.selectList(namespace+"selectAllWithId", id);
    }
}
