package com.fastcampus.comicbookrental.service;

import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.RentalDTO;
import com.fastcampus.comicbookrental.dto.SearchCondition;
import com.fastcampus.comicbookrental.dto.UserDTO;
import com.fastcampus.comicbookrental.repository.ComicbookDAO;
import com.fastcampus.comicbookrental.repository.RentalDAO;
import com.fastcampus.comicbookrental.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RentalServiceImpl implements RentalService {
    //트랜잭션으로 처리

    ComicbookDAO comicbookDAO;
    UserDAO userDAO;
    RentalDAO rentalDAO;
    @Autowired
    public RentalServiceImpl(ComicbookDAO comicbookDAO, UserDAO userDAO, RentalDAO rentalDAO){
        this.comicbookDAO=comicbookDAO;
        this.userDAO=userDAO;
        this.rentalDAO=rentalDAO;
    }

    //대여
    //(1) 대여
    //(2) 히스토리에 추가
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int rentalComicbook(ComicbookDTO dto, String id) throws Exception {
        if(rentalDAO.selectAllWithId(id).size()>0) throw new Exception();

        //(1) 대여
        Integer cno = dto.getCno();
        ComicbookDTO comicbookDTO = comicbookDAO.select(cno);
        System.out.println("comicbookDTO = " + comicbookDTO);
        //개선해야할 사항
        //왜 수량이 0이 나오는지 모르겠다.
//        if(comicbookDTO.getQuantity()<=0) throw new Exception();

        comicbookDTO.setQuantity(comicbookDTO.getQuantity()-1);
        int rowCnt= comicbookDAO.update(comicbookDTO);
        if(rowCnt!=1) throw new Exception();

        rowCnt = rentalDAO.insert(dto, id);
        if(rowCnt!=1) throw new Exception();

        Integer rno = rentalDAO.selectAll().get(0).getRno();
        RentalDTO rentalDTO=rentalDAO.select(rno);

        //(2) 히스토리에 추가
        rowCnt = rentalDAO.addRentalHistory(rentalDTO);
        if(rowCnt!=1) throw new Exception();

        return rowCnt;
    }

    //반납
    //(1) 반납
    //(2) 대여한 정보 찾기
    //(3) 찾은 정보에 반납한 일자 추가
    //(4) UserDTO에 며칠 연체인지 추가 (due_date -now() or return_date)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int returnComicbook(RentalDTO dto) throws Exception{
        //(1) 대여
        Integer cno = dto.getCno();
        ComicbookDTO comicbookDTO = comicbookDAO.select(dto.getCno());
        System.out.println("comicbookDTO = " + comicbookDTO);

        comicbookDTO.setQuantity(comicbookDTO.getQuantity()+1);
        int rowCnt= comicbookDAO.update(comicbookDTO);
        if(rowCnt!=1) throw new Exception();


        //(2) 히스토리에 추가
        rowCnt = rentalDAO.modifyRentalHistory(dto);
        if(rowCnt!=1) throw new Exception();


        rowCnt = rentalDAO.delete(dto);
        if(rowCnt!=1) throw new Exception();
        return rowCnt;
    }

    @Override
    public List<RentalDTO> getRentalListWithId(String id) throws Exception {
       return rentalDAO.selectAllWithId(id);
    }
    @Override
    public RentalDTO getDTO(Integer rno,String id, String isAdmin) throws Exception{
        RentalDTO rentalDTO = rentalDAO.select(rno);
        System.out.println("id = " + id);
        System.out.println("rno = " + rno);
        System.out.println("isAdmin = " + isAdmin);
        if(!rentalDTO.getId().equals(id) ||!isAdmin.equals("true") ) throw new Exception();

        return rentalDTO;
    }

    @Override
    public int getSearchResultCnt(SearchCondition sc) throws Exception {
        return rentalDAO.searchResultCnt(sc);
    }

    @Override
    public List<RentalDTO> getSearchResultPage(SearchCondition sc) throws Exception {
        return rentalDAO.searchSelectPage(sc);
    }
    @Override
    public List<RentalDTO> getList() throws Exception {
        return rentalDAO.selectAll();
    }

    @Override
    public List<RentalDTO> getPage(Map map) throws Exception {
        return rentalDAO.selectPage(map);
    }
}
