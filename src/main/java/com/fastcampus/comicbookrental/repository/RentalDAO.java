package com.fastcampus.comicbookrental.repository;

import com.fastcampus.comicbookrental.dto.RentalDTO;
import com.fastcampus.comicbookrental.dto.SearchCondition;

import java.util.List;
import java.util.Map;

public interface RentalDAO {
    //서비스 계층으로 예외 선언
    RentalDTO select(Integer rno) throws Exception;

    int count() throws Exception // T selectOne(String statement)
    ;

    int deleteAll() // int delete(String statement)
    ;

    //삭제시에는 rno와 id가 일치하는 경우나 관리자인경우
    int delete(RentalDTO dto) throws Exception // int delete(String statement, Object parameter)
    ;

    int insert(RentalDTO dto) throws Exception // int insert(String statement, Object parameter)
    ;

    List<RentalDTO> selectAll() throws Exception // List<E> selectList(String statement)
    ;

    List<RentalDTO> selectPage(Map map) throws Exception // List<E> selectList(String statement, Object parameter)
    ;

    //수정시에는 관리자인경우에만 가능
    //    public int update(RentalDTO dto,String isAdmin) throws Exception {
    int update(RentalDTO dto) throws Exception // int update(String statement, Object parameter)
    ;



    int searchResultCnt(SearchCondition sc) throws Exception // T selectOne(String statement, Object parameter)
    ;

    List<RentalDTO> searchSelectPage(SearchCondition sc) throws Exception // List<E> selectList(String statement, Object parameter)
    ;
    int addRentalHistory(RentalDTO dto) throws Exception;
    int modifyRentalHistory(RentalDTO dto) throws Exception;


    }
