package com.fastcampus.comicbookrental.service;

import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.SearchCondition;
import com.fastcampus.comicbookrental.repository.ComicbookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


//서비스에서는 예외처리를 수행하지않고, 모든 예외를 컨트롤러로 넘겨준다.
//: 트랜잭션 처리를 한다면 try-catch를 이용해 수행한다. (커밋과 롤백 수행을 위해)
@Service
public class ComicbookServiceImpl implements ComicbookService {
    @Autowired
    ComicbookDAO comicbookDAO;

    @Override
    public int getCount() throws Exception {
        return comicbookDAO.count();
    }

    @Override
    public int remove(Integer cno, String isAdmin) throws Exception {
        return comicbookDAO.delete(cno, isAdmin);
    }

    @Override
    public int write(ComicbookDTO comicbookDTO, String isAdmin) throws Exception {
        if(!isAdmin.equals("true")) throw new Exception();
        //예외처리 확인을 위한 예외발생
//        throw new Exception("write fail test");
        return comicbookDAO.insert(comicbookDTO);
    }



    @Override
    public ComicbookDTO read(Integer cno) throws Exception {
        ComicbookDTO comicbookDTO = comicbookDAO.select(cno);
        comicbookDAO.increaseViewCnt(cno);

        return comicbookDTO;
    }

    @Override
    public int modify(ComicbookDTO comicbookDTO, String isAdmin) throws Exception {
        if(!isAdmin.equals("true")) throw new Exception();
        return comicbookDAO.update(comicbookDTO);
    }

    @Override
    public int getSearchResultCnt(SearchCondition sc) throws Exception {
        return comicbookDAO.searchResultCnt(sc);
    }

    @Override
    public List<ComicbookDTO> getSearchResultPage(SearchCondition sc) throws Exception {
        return comicbookDAO.searchSelectPage(sc);
    }
    @Override
    public List<ComicbookDTO> getList() throws Exception {
        return comicbookDAO.selectAll();
    }

    @Override
    public List<ComicbookDTO> getPage(Map map) throws Exception {
        return comicbookDAO.selectPage(map);
    }
}
