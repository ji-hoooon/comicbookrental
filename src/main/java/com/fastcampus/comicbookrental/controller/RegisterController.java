package com.fastcampus.comicbookrental.controller;

import com.fastcampus.comicbookrental.dto.UserDTO;
import com.fastcampus.comicbookrental.repository.UserDAO;
import com.fastcampus.comicbookrental.service.UserService;
import com.fastcampus.comicbookrental.validator.UserDTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller // ctrl+shift+o 자동 import
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserService userService;


    @InitBinder
    public void toDate(WebDataBinder binder) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
        binder.setValidator(new UserDTOValidator()); // UserValidator를 WebDataBinder의 로컬 validator로 등록
        //	List<Validator> validatorList = binder.getValidators();
        //	System.out.println("validatorList="+validatorList);
    }

    @GetMapping("/add")
    public String register() {
        return "registerForm"; // WEB-INF/views/registerForm.jsp
    }

    @PostMapping("/save")
    public String save(@Valid UserDTO user, BindingResult result, Model m) throws Exception {
        System.out.println("result="+result);
        System.out.println("user="+user);
        return userService.userSave(user, result);
    }

    private boolean isValid(UserDTO user) {
        return true;
    }
}