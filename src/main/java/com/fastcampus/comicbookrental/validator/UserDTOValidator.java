package com.fastcampus.comicbookrental.validator;

import com.fastcampus.comicbookrental.dto.UserDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
//		return UserDTO.class.equals(clazz); // 검증하려는 객체가 UserDTO타입인지 확인
        return UserDTO.class.isAssignableFrom(clazz); // clazz가 UserDTO 또는 그 자손인지 확인
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("UserDTOValidator.validate() is called");

        UserDTO user = (UserDTO)target;

        String id = user.getId();

//		if(id==null || "".equals(id.trim())) {
//			errors.rejectValue("id", "required");
//		}
        //앞뒤 공백을 잘라서 빈문자열인지 확인하는 클래스와 static메서드 (유틸리티클래스)
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id",  "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");

        if(id==null || id.length() <  5 || id.length() > 12) {
            errors.rejectValue("id", "invalidLength", new String[] {"5", "12"}, null);
        }
    }
}