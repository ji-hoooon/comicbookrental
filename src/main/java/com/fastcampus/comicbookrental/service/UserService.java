package com.fastcampus.comicbookrental.service;

import com.fastcampus.comicbookrental.dto.SearchCondition;
import com.fastcampus.comicbookrental.dto.UserDTO;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface UserService {
    //회원 가입
    String userSave(@Valid UserDTO user, BindingResult result) throws Exception;

    //회원 추가
    int userAdd(UserDTO dto, String isAdmin) throws Exception;

    //회원 삭제
    int userRemove(String id, String isAdmin) throws Exception;

    //(1) 관리자 이거나 본인 여부 확인
    //(2) 읽기, 수정 페이지 함께 사용
    //회원 정보 수정
    int userModify(UserDTO userDTO, String isAdmin) throws Exception;

    int getCount() throws Exception;

    List<UserDTO> getList(String isAdmin) throws Exception;

    List<UserDTO> getPage(Map map) throws Exception;

    UserDTO userRead(String id, String isAdmin) throws Exception;

    int getSearchResultCnt(SearchCondition sc) throws Exception;

    List<UserDTO> getSearchResultPage(SearchCondition sc) throws Exception;
}
