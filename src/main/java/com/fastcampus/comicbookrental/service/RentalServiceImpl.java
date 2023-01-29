package com.fastcampus.comicbookrental.service;

import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.RentalDTO;
import com.fastcampus.comicbookrental.repository.ComicbookDAO;
import com.fastcampus.comicbookrental.repository.RentalDAO;
import com.fastcampus.comicbookrental.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void rentalComicbook(RentalDTO dto) throws Exception {
        //(1) 대여
        Integer cno = dto.getCno();
        ComicbookDTO comicbookDTO = comicbookDAO.select(cno);
        System.out.println("comicbookDTO = " + comicbookDTO);
//        if(comicbookDTO.getQuantity()<1) throw new Exception();

        comicbookDTO.setQuantity(comicbookDTO.getQuantity()-1);
        int rowCnt= comicbookDAO.update(comicbookDTO);
        if(rowCnt!=1) throw new Exception();

        rowCnt = rentalDAO.insert(dto);
        if(rowCnt!=1) throw new Exception();

        //(2) 히스토리에 추가
        rowCnt = rentalDAO.addRentalHistory(dto);
        if(rowCnt!=1) throw new Exception();
    }

    //반납
    //(1) 반납
    //(2) 대여한 정보 찾기
    //(3) 찾은 정보에 반납한 일자 추가
    //(4) UserDTO에 며칠 연체인지 추가 (due_date -now() or return_date)
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnComicbook(RentalDTO dto) throws Exception{
        //(1) 대여
        Integer cno = dto.getCno();
        ComicbookDTO comicbookDTO = comicbookDAO.select(dto.getRno());
        System.out.println("comicbookDTO = " + comicbookDTO);

        comicbookDTO.setQuantity(comicbookDTO.getQuantity()+1);
        int rowCnt= comicbookDAO.update(comicbookDTO);
        if(rowCnt!=1) throw new Exception();


        //(2) 히스토리에 추가
        rowCnt = rentalDAO.modifyRentalHistory(dto);
        if(rowCnt!=1) throw new Exception();


        rowCnt = rentalDAO.delete(dto);
        if(rowCnt!=1) throw new Exception();
    }
}
