package com.fastcampus.comicbookrental.repository;
import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.SearchCondition;
import com.fastcampus.comicbookrental.dto.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class ComicbookDaoImplTest {

    @Autowired
    ComicbookDAO comicbookDAO;

    @Test
    public void searchSelectPageTest() throws Exception {
        comicbookDAO.deleteAll();
        for(int i=1;i<=20;i++){
            ComicbookDTO comicbookDTO= new ComicbookDTO("title"+i,  "asdf"+i,"asdfasdf");
            comicbookDAO.insert(comicbookDTO);
        }
        SearchCondition sc = new SearchCondition(1, 10, "title2", "T");
        List<ComicbookDTO> list = comicbookDAO.searchSelectPage(sc);
        System.out.println("list = " + list);
        assertTrue(list.size()==2); //1-20, title2, title20

        sc = new SearchCondition(1, 10, "asdf2", "W");  //asdf2%
        list = comicbookDAO.searchSelectPage(sc);
        System.out.println("list = " + list);
        assertTrue(list.size()==2); //1-20, asdf2, asdf20
    }
    @Test
    public void searchSelectCntTest() throws Exception {
        comicbookDAO.deleteAll();
        for(int i=1;i<=20;i++){
            ComicbookDTO comicbookDTO= new ComicbookDTO("title"+i, "asdf"+i,"asdfasdf" );
            comicbookDAO.insert(comicbookDTO);
        }
        SearchCondition sc = new SearchCondition(1, 10, "title2", "T");
        int cnt = comicbookDAO.searchResultCnt(sc);
        System.out.println("cnt = " + cnt);
        assertTrue(cnt==2); //title2, title20

        sc = new SearchCondition(1, 10, "asdf2", "W");  //asdf2%
        cnt = comicbookDAO.searchResultCnt(sc);
        System.out.println("cnt = " + cnt);
        assertTrue(cnt==2); //1-20, asdf2, asdf20
    }



    @Test
    public void InsertTestData() throws Exception{
        comicbookDAO.deleteAll();
        for(int i=1; i<=220; i++){
            ComicbookDTO comicbookDTO = new ComicbookDTO("title"+i, "no content", "asdf" );
            comicbookDAO.insert(comicbookDTO);
        }
    }


    @Test
    public void countTest() throws Exception {
        comicbookDAO.deleteAll();
        assertTrue(comicbookDAO.count()==0);

        ComicbookDTO comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        assertTrue(comicbookDAO.count()==1);

        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        assertTrue(comicbookDAO.count()==2);
    }

    @Test
    public void deleteAllTest() throws Exception {
        comicbookDAO.deleteAll();
        assertTrue(comicbookDAO.count()==0);

        ComicbookDTO comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        assertTrue(comicbookDAO.deleteAll()==1);
        assertTrue(comicbookDAO.count()==0);

        comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        assertTrue(comicbookDAO.deleteAll()==2);
        assertTrue(comicbookDAO.count()==0);
    }

    @Test
    public void deleteTest() throws Exception {
        comicbookDAO.deleteAll();
        assertTrue(comicbookDAO.count()==0);

        ComicbookDTO comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        Integer cno = comicbookDAO.selectAll().get(0).getCno();
        assertTrue(comicbookDAO.delete(cno, "true")==1);
        assertTrue(comicbookDAO.count()==0);

        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        cno = comicbookDAO.selectAll().get(0).getCno();
        assertTrue(comicbookDAO.delete(cno, "false")==0);
        assertTrue(comicbookDAO.count()==1);

        assertTrue(comicbookDAO.delete(cno, "true")==1);
        assertTrue(comicbookDAO.count()==0);

        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        cno = comicbookDAO.selectAll().get(0).getCno();
        assertTrue(comicbookDAO.delete(cno+1, comicbookDTO.getWriter())==0);
        assertTrue(comicbookDAO.count()==1);
    }

    @Test
    public void insertTest() throws Exception {
        comicbookDAO.deleteAll();
        ComicbookDTO comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);

        comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        assertTrue(comicbookDAO.count()==2);

        comicbookDAO.deleteAll();
        comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        assertTrue(comicbookDAO.count()==1);
    }

    @Test
    public void selectAllTest() throws Exception {
        comicbookDAO.deleteAll();
        assertTrue(comicbookDAO.count()==0);

        List<ComicbookDTO> list = comicbookDAO.selectAll();
        assertTrue(list.size() == 0);

        ComicbookDTO comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);

        list = comicbookDAO.selectAll();
        assertTrue(list.size() == 1);

        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        list = comicbookDAO.selectAll();
        assertTrue(list.size() == 2);
    }

//    @Test
//    public void selectOneTest() throws Exception {
//        assertTrue(comicbookDAO!=null);
//        System.out.println("comicbookDAO = " + comicbookDAO);
//        ComicbookDTO comicbookDTO = comicbookDAO.select(2);
//        //comicbookDTO.soutv
//        System.out.println("comicbookDTO = " + comicbookDTO);
//        assertTrue(comicbookDTO.getCno().equals(2));
//    }

    @Test
    public void selectTest() throws Exception {
        comicbookDAO.deleteAll();
        assertTrue(comicbookDAO.count()==0);

        ComicbookDTO comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);

        Integer cno = comicbookDAO.selectAll().get(0).getCno();
        comicbookDTO.setCno(cno);
        ComicbookDTO comicbookDTO2 = comicbookDAO.select(cno);
//        assertTrue(comicbookDTO.equals(comicbookDTO2));
        assertTrue(comicbookDTO.getCno().equals(comicbookDTO2.getCno()));
    }

    @Test
    public void selectPageTest() throws Exception {
        comicbookDAO.deleteAll();

        for (int i = 1; i <= 10; i++) {
            ComicbookDTO comicbookDTO = new ComicbookDTO(""+i, "no content"+i, "asdf");
            comicbookDAO.insert(comicbookDTO);
        }

        Map map = new HashMap();
        map.put("offset", 0);
        map.put("pageSize", 3);

        List<ComicbookDTO> list = comicbookDAO.selectPage(map);
        assertTrue(list.get(0).getTitle().equals("10"));
        assertTrue(list.get(1).getTitle().equals("9"));
        assertTrue(list.get(2).getTitle().equals("8"));

        map = new HashMap();
        map.put("offset", 0);
        map.put("pageSize", 1);

        list = comicbookDAO.selectPage(map);
        assertTrue(list.get(0).getTitle().equals("10"));

        map = new HashMap();
        map.put("offset", 7);
        map.put("pageSize", 3);

        list = comicbookDAO.selectPage(map);
        assertTrue(list.get(0).getTitle().equals("3"));
        assertTrue(list.get(1).getTitle().equals("2"));
        assertTrue(list.get(2).getTitle().equals("1"));
    }

    @Test
    public void updateTest() throws Exception {
        comicbookDAO.deleteAll();
        ComicbookDTO comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);

        Integer cno = comicbookDAO.selectAll().get(0).getCno();
        System.out.println("cno = " + cno);
        comicbookDTO.setCno(cno);
        comicbookDTO.setTitle("yes title");
        assertTrue(comicbookDAO.update(comicbookDTO)==1);

        ComicbookDTO comicbookDTO2 = comicbookDAO.select(cno);
//        assertTrue(comicbookDTO.equals(comicbookDTO2));
        assertTrue(comicbookDTO.getCno().equals(comicbookDTO2.getCno()));
    }

    @Test
    public void increaseViewCntTest() throws Exception {
        comicbookDAO.deleteAll();
        assertTrue(comicbookDAO.count()==0);

        ComicbookDTO comicbookDTO = new ComicbookDTO("no title", "no content", "asdf");
        assertTrue(comicbookDAO.insert(comicbookDTO)==1);
        assertTrue(comicbookDAO.count()==1);

        Integer cno = comicbookDAO.selectAll().get(0).getCno();
        assertTrue(comicbookDAO.increaseViewCnt(cno)==1);

        comicbookDTO = comicbookDAO.select(cno);
        assertTrue(comicbookDTO!=null);
        assertTrue(comicbookDTO.getView_cnt() == 1);

        assertTrue(comicbookDAO.increaseViewCnt(cno)==1);
        comicbookDTO = comicbookDAO.select(cno);
        assertTrue(comicbookDTO!=null);
        assertTrue(comicbookDTO.getView_cnt() == 2);
    }



}