package com.fastcampus.comicbookrental.controller;

import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.PageHandler;
import com.fastcampus.comicbookrental.dto.SearchCondition;
import com.fastcampus.comicbookrental.service.ComicbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/comicbook")
public class ComicbookController {
    @Autowired
    ComicbookService comicbookService;
    //수정
    @GetMapping("/modify")
    public String modify(Model m){
        m.addAttribute("mode", "new");
        return "comicbook"; //읽기와 쓰기에 사용, 쓰기에 사용할때는 mode=new
    }

    @PostMapping("/modify")
    public String modify(ComicbookDTO comicbookDTO, HttpSession session, Model m, RedirectAttributes rattr){
        //관리자인지 정보를 얻기 위한 문자열
        String isAdmin = (String) session.getAttribute("isAdmin");

        try {
            int rowCnt =comicbookService.modify(comicbookDTO, isAdmin);

            //DB에 제대로 등록되었는지 확인
            if(rowCnt!=1){
                throw new Exception("Modify Failed");
            }
            rattr.addFlashAttribute("msg", "MOD_OK");

            return "redirect:/comicbook/list";
        } catch (Exception e) {
            e.printStackTrace();
            //에러 발생시 작성하던 글로 이동하는데, 작성하던 글 정보를 담아서 전송
            m.addAttribute(comicbookDTO);
            m.addAttribute("msg", "MOD_ERR");
            return "comicbook";
        }
    }

    //쓰기
    @GetMapping("/write")
    public String write(Model m){
        m.addAttribute("mode", "new");
        return "comicbook"; //읽기와 쓰기에 사용, 쓰기에 사용할때는 mode=new
    }

    @PostMapping("/write")
    public String write(ComicbookDTO comicbookDTO, HttpSession session, Model m, RedirectAttributes rattr){
        //등록하는 사용자가 관리자인지 얻기 위한 문자열
        String isAdmin = (String) session.getAttribute("isAdmin");
        try {
            int rowCnt =comicbookService.write(comicbookDTO, isAdmin);

            //DB에 제대로 등록되었는지 확인
            if(rowCnt!=1){
                throw new Exception("Write Failed");
            }
            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/comicbook/list";
        } catch (Exception e) {
            e.printStackTrace();
            //에러 발생시 작성하던 만화책으로 이동하는데, 작성하던 만화책 정보를 담아서 전송
            m.addAttribute(comicbookDTO);
            m.addAttribute("msg", "WRT_ERR");
            return "comicbook";
        }
    }

    //삭제
    @PostMapping("/remove")
    public String remove(HttpSession session, Integer cno, Integer page, Integer pageSize, Model m, RedirectAttributes rattr){
        //HttpSession으로 세션 정보가져와서, 속성값인 id를 String으로 저장
       String isAdmin=(String)session.getAttribute("isAdmin");
        try {
            //현재 페이지 정보와 페이지 크기 정보 전달하면, 리다이렉트시 쿼리스트링으로 자동생성
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            //remove 메서드 호출해 관리자인 경우 맞으면 삭제
            //: 만화책에서 삭제 버튼이 안보이도록 1차 처리
            int rowCnt= comicbookService.remove(cno,isAdmin);
            if(rowCnt!=1)
            throw new Exception("comicbook remove error");
            {
//                m.addAttribute("msg", URLEncoder.encode("삭제되었습니다."));
                m.addAttribute("msg", "DEL_OK");
                return "redirect:/comicbook/list";
            }
        } catch (Exception e) {
            e.printStackTrace();
//            m.addAttribute("msg", "DEL_ERR");
            //세션에 저장해 일회성으로 전달
            rattr.addFlashAttribute("msg", "DEL_ERR");
        }
        return "redirect:/comicbook/list";
    }

    //읽기
    @GetMapping("/read")
    public String read(Integer cno, Integer page, Integer pageSize,Model m){
        try {
            ComicbookDTO comicbookDTO=comicbookService.read(cno);
//            m.addAttribute("comicbookDTO", comicbookDTO);
            m.addAttribute(comicbookDTO);
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "comicbook";
    }


    @GetMapping("/list")
        public String list (@ModelAttribute SearchCondition sc, Model m, HttpServletRequest request){

            if (!loginCheck(request))
                return "redirect:/login/login?toURL=" + request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

            try {
                int totalCnt = comicbookService.getSearchResultCnt(sc);
                m.addAttribute("totalCnt", totalCnt);

                PageHandler pageHandler = new PageHandler(totalCnt, sc);

                List<ComicbookDTO> list = comicbookService.getSearchResultPage(sc);
                m.addAttribute("list", list);
                m.addAttribute("ph", pageHandler);

                Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
                m.addAttribute("startOfToday", startOfToday.toEpochMilli());
            } catch (Exception e) {
                e.printStackTrace();
                m.addAttribute("msg", "LIST_ERR");
                m.addAttribute("totalCnt", 0);
            }

            return "comicbookList"; // 로그인을 한 상태이면, 만화책 목록 화면으로 이동
        }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}
