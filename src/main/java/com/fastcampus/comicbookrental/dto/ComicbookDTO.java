package com.fastcampus.comicbookrental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
public class ComicbookDTO {
    private Integer cno;
    private String title;
    private String writer;
    private String publisher;
    private Date release;
    private int quantity;
    private boolean adult;
    private Date up_date;
    private int view_cnt;
    private Date reg_date;

    public ComicbookDTO(String title, String writer, String publisher) {
        this.title = title;
        this.writer = writer;
        this.publisher = publisher;
//        this.release = release;
    }
}
