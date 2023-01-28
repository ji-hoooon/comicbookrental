package com.fastcampus.comicbookrental.service;

import com.fastcampus.comicbookrental.dto.ComicbookDTO;
import com.fastcampus.comicbookrental.dto.SearchCondition;

import java.util.List;
import java.util.Map;

public interface ComicbookService {
    int getCount() throws Exception;

    int remove(Integer cno, String isAdmin) throws Exception;

    int write(ComicbookDTO comicbookDTO, String isAdmin) throws Exception;

    List<ComicbookDTO> getList() throws Exception;

    List<ComicbookDTO> getPage(Map map) throws Exception;

    ComicbookDTO read(Integer cno) throws Exception;

    int modify(ComicbookDTO comicbookDTO, String isAdmin) throws Exception;

    int getSearchResultCnt(SearchCondition sc) throws Exception;

    List<ComicbookDTO> getSearchResultPage(SearchCondition sc) throws Exception;
}
