package com.fastcampus.comicbookrental.repository;

import com.fastcampus.comicbookrental.dto.UserDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    //root-context에 등록한 SqlSessiontemplate 의존성 주입
    @Autowired
    SqlSession session;

    //mapper 네임스페이스 지정 sql id을 뒤에 추가하기 위해 마지막에 '.'
    String namespace="com.fastcampus.comicbookrental.dao.UserMapper.";

    @Override
    public UserDTO selectUser(String id) throws Exception {
        return session.selectOne(namespace+"select", id);
    }

    @Override
    public int deleteUser(String id) throws Exception {
        return session.delete(namespace+"delete", id);
    }

    @Override
    public int insertUser(UserDTO dto) throws Exception {
        return session.insert(namespace+"insert", dto);
    }

    @Override
    public int updateUser(UserDTO dto) throws Exception {
        return session.update(namespace+"update",dto);
    }

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    }

    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace+"deleteAll");
    }
}
