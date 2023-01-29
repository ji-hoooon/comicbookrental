package com.fastcampus.comicbookrental.service;

import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.RentalDTO;
import com.fastcampus.comicbookrental.dto.UserDTO;
import com.fastcampus.comicbookrental.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class RentalServiceImplTest {
    @Autowired
    RentalDAOImpl rentalDAO;

    @Autowired
    ComicbookDAOImpl comicbookDAO;

    @Autowired
    UserDAO userDAO;
    @Autowired
    RentalService rentalService;
    @Test
    public void rentalComicbook() throws Exception {
        rentalDAO.deleteAll();
        comicbookDAO.deleteAll();
        userDAO.deleteAll();

        UserDTO userDTO=new UserDTO("asdf", new Date(), "asdf@naver.com","010-1234-1234",true,"asdf","asdf");
        int rowCnt=userDAO.insertUser(userDTO);
        assertTrue(rowCnt==1);

        ComicbookDTO comicbookDTO = new ComicbookDTO("title", "no content", "asdf" );
        rowCnt=comicbookDAO.insert(comicbookDTO);
        assertTrue(rowCnt==1);

        Integer cno = comicbookDAO.selectAll().get(0).getCno();
        comicbookDTO.setCno(cno);

        RentalDTO rentalDTO = new RentalDTO(comicbookDTO.getCno(), userDTO.getId(), new Date());
        rowCnt=rentalDAO.insert(rentalDTO);
        assertTrue(rowCnt==1);
        Integer rno = comicbookDAO.selectAll().get(0).getCno();
        rentalDTO.setRno(rno);
        rentalService.rentalComicbook(rentalDTO);
    }

    @Test
    public void returnComicbook() {
        rentalDAO.deleteAll();
    }
}