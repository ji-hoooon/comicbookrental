package com.fastcampus.comicbookrental.repository;


import com.fastcampus.comicbookrental.dto.SearchCondition;
import com.fastcampus.comicbookrental.dto.UserDTO;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    UserDTO selectUser(String id) throws Exception;
    int deleteUser(String id) throws Exception;
    int insertUser(UserDTO user) throws Exception;
    int updateUser(UserDTO user) throws Exception;
    int count() throws Exception;
    int deleteAll() throws Exception;
    List<UserDTO> selectAll() throws Exception;

    List<UserDTO> selectPage(Map map) throws Exception ;

    int searchResultCnt(SearchCondition sc) throws Exception;
    List<UserDTO> searchSelectPage(SearchCondition sc) throws Exception;
}
