package com.fastcampus.comicbookrental.repository;


import com.fastcampus.comicbookrental.dto.UserDTO;

public interface UserDAO {
    UserDTO selectUser(String id) throws Exception;
    int deleteUser(String id) throws Exception;
    int insertUser(UserDTO user) throws Exception;
    int updateUser(UserDTO user) throws Exception;
    int count() throws Exception;
    int deleteAll() throws Exception;
}
