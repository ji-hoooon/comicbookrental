package com.fastcampus.comicbookrental.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RentalDTO {
    private Integer rno;
    private Date due_date;
    private Integer cno;
    private String id;

    public RentalDTO(Date due_date, Integer cno, String id) {
        this.due_date = due_date;
        this.cno = cno;
        this.id = id;
    }
}
