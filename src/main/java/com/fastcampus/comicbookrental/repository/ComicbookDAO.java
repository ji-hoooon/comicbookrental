package com.fastcampus.comicbookrental.repository;

import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.SearchCondition;

import java.util.List;
import java.util.Map;

public interface ComicbookDAO {
    //서비스 계층으로 예외 선언
    ComicbookDTO select(Integer cno) throws Exception;

    int count() throws Exception // T selectOne(String statement)
    ;

    int deleteAll() // int delete(String statement)
    ;

    //삭제시에는 게시물 번호와 작성자가 일치할 때만
    int delete(Integer cno, String isAdmin) throws Exception // int delete(String statement, Object parameter)
    ;

    int insert(ComicbookDTO dto) throws Exception // int insert(String statement, Object parameter)
    ;
    int insertWithQuantity(ComicbookDTO dto) throws Exception;


        List<ComicbookDTO> selectAll() throws Exception // List<E> selectList(String statement)
    ;

    List<ComicbookDTO> selectPage(Map map) throws Exception // List<E> selectList(String statement, Object parameter)
    ;

    int update(ComicbookDTO dto) throws Exception // int update(String statement, Object parameter)
    ;

    int increaseViewCnt(Integer cno) throws Exception // int update(String statement, Object parameter)
    ;

    int searchResultCnt(SearchCondition sc) throws Exception // T selectOne(String statement, Object parameter)
    ;

    List<ComicbookDTO> searchSelectPage(SearchCondition sc) throws Exception // List<E> selectList(String statement, Object parameter)
    ;

}
