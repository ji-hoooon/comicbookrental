package com.fastcampus.comicbookrental.repository;

import com.fastcampus.comicbookrental.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class UserDAOImplTest {
    @Autowired
    UserDAO userDAO;

    @Test
    public void selectUser() throws Exception {
        userDAO.deleteAll();
        assertTrue(userDAO.count()==0);
//        String id, Date birth, String email, String tel, String sex, String pwd, String name)
        UserDTO userDTO=new UserDTO("asdf", new Date(), "asdf@naver.com","010-1234-1234",true,"asdf","asdf");
//        UserDTO userDTO = new UserDTO("asdf", new Date(), "asdf@naver.com", "010-1234-1234","Male", false, new Date(),true,"asdf","asdf");
        int rowCnt=userDAO.insertUser(userDTO);
        assertTrue(rowCnt==1);
        String id = "asdf";
        UserDTO userDTO2=userDAO.selectUser(id);
        assertTrue(userDTO.getId().equals(userDTO2.getId()));
    }

    @Test
    public void deleteUser() throws Exception {
        userDAO.deleteAll();

    }

    @Test
    public void insertUser() throws Exception {
        userDAO.deleteAll();

    }

    @Test
    public void updateUser() throws Exception {
        userDAO.deleteAll();

    }

    @Test
    public void count() throws Exception {
        userDAO.deleteAll();


    }

    @Test
    public void deleteAll() throws Exception {
        userDAO.deleteAll();

    }
}