package com.fastcampus.comicbookrental.service;

import com.fastcampus.comicbookrental.dto.UserDTO;
import com.fastcampus.comicbookrental.dto.SearchCondition;
import com.fastcampus.comicbookrental.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    final int FAIL = 0;

    //회원 가입
    @Override
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

    @Override
    public int userAdd(UserDTO dto, String isAdmin) throws Exception {
        if(!isAdmin.equals("true")) throw new Exception();
        return userDAO.insertUser(dto);
    }

    //회원 삭제
    @Override
    public int userRemove(String id, String isAdmin) throws Exception {
        if(!isAdmin.equals("true")) throw new Exception();
        return userDAO.deleteUser(id);
    }
    //(1) 관리자 이거나 본인 여부 확인
    //(2) 읽기, 수정 페이지 함께 사용
    //회원 정보 수정
    @Override
    public int userModify(UserDTO userDTO, String isAdmin) throws Exception {
        if(!isAdmin.equals("true")) throw new Exception();
        return userDAO.updateUser(userDTO);
    }
    //(1) 관리자이거나 본인 여부 확인
    //(2) 읽기, 수정 페이지 함께 사용


    @Override
    public int getCount() throws Exception {
        return userDAO.count();
    }

    //회원 목록 출력
    @Override
    public List<UserDTO> getList(String isAdmin) throws Exception {
        //(1) 관리자 일때만 호출 가능하도록
        if(!isAdmin.equals("true")) throw new Exception();
        return userDAO.selectAll();
    }


    @Override
    public UserDTO userRead(String id, String isAdmin) throws Exception {
        UserDTO userDTO = userDAO.selectUser(id);
        if(!userDTO.getId().equals(id) ||!isAdmin.equals("true") ) throw new Exception();
        return userDTO;
    }

    @Override
    public List<UserDTO> getPage(Map map) throws Exception {
        return userDAO.selectPage(map);
    }

    @Override
    public int getSearchResultCnt(SearchCondition sc) throws Exception {
        return userDAO.searchResultCnt(sc);
    }

    @Override
    public List<UserDTO> getSearchResultPage(SearchCondition sc) throws Exception {
        return userDAO.searchSelectPage(sc);
    }
}
