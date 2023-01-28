package com.fastcampus.comicbookrental.service;

import com.fastcampus.comicbookrental.dto.RentalDTO;
import org.springframework.transaction.annotation.Transactional;

public interface RentalService {
    //대여
    //(1) 대여
    //(2) 히스토리에 추가
    @Transactional(rollbackFor = Exception.class)
    void rentalComicbook(RentalDTO dto, String id) throws Exception;

    //반납
    //(1) 반납
    //(2) 대여한 정보 찾기
    //(3) 찾은 정보에 반납한 일자 추가
    //(4) UserDTO에 며칠 연체인지 추가 (due_date -now() or return_date)
    @Transactional(rollbackFor = Exception.class)
    void returnComicbook(RentalDTO dto, String id) throws Exception;
}
