package com.fastcampus.comicbookrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDTO {
    private Integer uno;
    private String id;
    private Date birth;
    private String email;
    private String tel;
    private boolean sex;
    private boolean sns;
    private Date isdeniedrental;
    private boolean isadmin;
    private Date reg_date;
    private String pwd;
    private String name;
    private Date up_date;


    public UserDTO(){}
    public UserDTO(String id, Date birth, String email, String tel, boolean sex, String pwd, String name) {
        this.id = id;
        this.birth = birth;
        this.email = email;
        this.tel = tel;
        this.sex = sex;
        this.pwd = pwd;
        this.name = name;
    }
}
