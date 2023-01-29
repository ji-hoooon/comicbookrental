package com.fastcampus.comicbookrental.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RentalDTO {
    private Integer rno;
    private Date due_date;
    private Integer cno;
    private String id;

    private Date rental_date;

    public RentalDTO(Integer cno, String id, Date rental_date) {
        this.cno = cno;
        this.id = id;
        this.rental_date=rental_date;
    }
}
