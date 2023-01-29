package com.fastcampus.comicbookrental.controller;

import com.fastcampus.comicbookrental.dto.RentalDTO;
import com.fastcampus.comicbookrental.service.RentalService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RentalController {
    @Autowired
    RentalService service;

    // 지정된 대여한 만화책을 삭제하는 메서드로 URI로 작성되었으므로 {}으로 감싸줘야한다.
    @DeleteMapping("/rentals/{rno}")  // DELETE /rentals/1?id=id  <-- 삭제할 대여한 만화책 번호
    //: 맵핑된 uri의 일부를 읽어오기 위해서는 @PathVariable 어노테이션을 이용해야 한다. (쿼리스트링이 아닌경우 필요)
    public ResponseEntity<String> remove(@PathVariable Integer rno,String id, HttpSession session) {
        String isAdmin=(String)session.getAttribute("isAdmin");
//        String id = (String)session.getAttribute("id");

        try {
            RentalDTO dto =service.getDTO(rno, id, isAdmin);
            int rowCnt = service.returnComicbook(dto);
            System.out.println("결과값 : rowCnt = " + rowCnt);
            if(rowCnt!=1)
                throw new Exception("Delete Failed");
            System.out.println("결과값 : rowCnt = " + rowCnt);

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 지정된 유저의 모든 대여한 만화책을 가져오는 메서드
    @GetMapping("/rentals")  // /rentals?id=id   GET
    public ResponseEntity<List<RentalDTO>> list(String id) {
        List<RentalDTO> list = null;
        try {
            list = service.getRentalListWithId(id);
            return new ResponseEntity<List<RentalDTO>>(list, HttpStatus.OK);  // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<RentalDTO>>(HttpStatus.BAD_REQUEST); // 400
        }
    }
}
