package com.fastcampus.comicbookrental.service;

import com.fastcampus.comicbookrental.dto.UserDTO;
import com.fastcampus.comicbookrental.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;
    final int FAIL = 0;

    //회원 가입
    public String userSave(@Valid UserDTO user, BindingResult result) throws Exception {

        // User객체를 검증한 결과 에러가 있으면, registerForm을 이용해서 에러를 보여줘야 함.
        if(!result.hasErrors()) {
            // 2. DB에 신규회원 정보를 저장
            int rowCnt = userDAO.insertUser(user);

            if(rowCnt!=FAIL) {
                return "redirect:/";
            }
            //개선해야하는 사항
            //1. 로그인상태유지하기
            //2. 뒤로가기하면 정보지운상태보여주기
            //3. id랑 이메일 중복체크하기
        }
        return "registerForm";
    }
    //회원 삭제
    //회원 정보 수정
    //회원 목록 출력
}
