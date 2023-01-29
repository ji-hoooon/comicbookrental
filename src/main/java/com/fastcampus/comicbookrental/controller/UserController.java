package com.fastcampus.comicbookrental.controller;

import com.fastcampus.comicbookrental.dto.PageHandler;
import com.fastcampus.comicbookrental.dto.SearchCondition;
import com.fastcampus.comicbookrental.dto.UserDTO;
import com.fastcampus.comicbookrental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserService userService;
    //수정
    @GetMapping("/modify")
    public String modify(Model m){
        m.addAttribute("mode", "new");
        return "user"; //읽기와 쓰기에 사용, 쓰기에 사용할때는 mode=new
    }

    @PostMapping("/modify")
    public String modify(UserDTO userDTO, HttpSession session, Model m, RedirectAttributes rattr){
        //관리자인지 정보를 얻기 위한 문자열
        String isAdmin = (String) session.getAttribute("isAdmin");
        if (!isAdmin.equals("true"))
            return "redirect:/";  // 관리자가 아니면 홈 화면으로 이동
        try {
            int rowCnt =userService.userModify(userDTO, isAdmin);

            //DB에 제대로 등록되었는지 확인
            if(rowCnt!=1){
                throw new Exception("Modify Failed");
            }
            rattr.addFlashAttribute("msg", "MOD_OK");

            return "redirect:/admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            //에러 발생시 작성하던 글로 이동하는데, 작성하던 글 정보를 담아서 전송
            m.addAttribute(userDTO);
            m.addAttribute("msg", "MOD_ERR");
            return "user";
        }
    }


    //삭제
    @PostMapping("/remove")
    public String remove(HttpSession session, String id, Integer page, Integer pageSize, Model m, RedirectAttributes rattr){
        //HttpSession으로 세션 정보가져와서, 속성값인 id를 String으로 저장
        String isAdmin=(String)session.getAttribute("isAdmin");
        if (!isAdmin.equals("true"))
            return "redirect:/";  // 관리자가 아니면 홈 화면으로 이동
        try {
            //현재 페이지 정보와 페이지 크기 정보 전달하면, 리다이렉트시 쿼리스트링으로 자동생성
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            //remove 메서드 호출해 관리자인 경우 맞으면 삭제
            //: 만화책에서 삭제 버튼이 안보이도록 1차 처리
            int rowCnt= userService.userRemove(id,isAdmin);
            if(rowCnt!=1)
                throw new Exception("user remove error");
            {
//                m.addAttribute("msg", URLEncoder.encode("삭제되었습니다."));
                m.addAttribute("msg", "DEL_OK");
                return "redirect:/admin/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
//            m.addAttribute("msg", "DEL_ERR");
            //세션에 저장해 일회성으로 전달
            rattr.addFlashAttribute("msg", "DEL_ERR");
        }
        return "redirect:/admin/list";
    }
    //사용자추가
    @GetMapping("/write")
    public String write(Model m){
        m.addAttribute("mode", "new");
        return "user"; //읽기와 쓰기에 사용, 쓰기에 사용할때는 mode=new
    }

    @PostMapping("/write")
    public String write(UserDTO userDTO, HttpSession session, Model m, RedirectAttributes rattr){
        //등록하는 사용자가 관리자인지 얻기 위한 문자열
        System.out.println("관리자가 사용자 추가");
        String isAdmin = (String) session.getAttribute("isAdmin");
        if (!isAdmin.equals("true"))
            return "redirect:/";  // 관리자가 아니면 홈 화면으로 이동
        try {
            int rowCnt =userService.userAdd(userDTO, isAdmin);

            //DB에 제대로 등록되었는지 확인
            if(rowCnt!=1){
                throw new Exception("Write Failed");
            }
            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/admin/list";
        } catch (Exception e) {
            e.printStackTrace();
            //에러 발생시 작성하던 사용자로 이동하는데, 작성하던 사용자 정보를 담아서 전송
            m.addAttribute(userDTO);
            m.addAttribute("msg", "WRT_ERR");
            return "user";
        }
    }
    //읽기
    @GetMapping("/read")
    public String read(String id, Integer page, Integer pageSize,Model m, HttpSession session){
        String isAdmin=(String)session.getAttribute("isAdmin");
        if (!isAdmin.equals("true"))
            return "redirect:/";  // 관리자가 아니면 홈 화면으로 이동
        try {
            UserDTO userDTO=userService.userRead(id, isAdmin);
//            m.addAttribute("userDTO", userDTO);
            m.addAttribute(userDTO);
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "user";
    }


    @GetMapping("/list")
    public String list (@ModelAttribute SearchCondition sc, Model m, HttpSession session){
        String isAdmin=(String)session.getAttribute("isAdmin");

        if (!isAdmin.equals("true"))
            return "redirect:/";  // 관리자가 아니면 홈 화면으로 이동
        try {
            int totalCnt = userService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);

            List<UserDTO> list = userService.getSearchResultPage(sc);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }

        return "userList"; // 로그인을 한 상태이면, 만화책 목록 화면으로 이동
    }
}
