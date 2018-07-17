package com.snow.lib.result;


import org.springframework.data.domain.Page;

/**
 * Created by SNOW on 2018.01.30.
 */
public class PageUtil {

    private PageUtil() {
    }

    public static Pagination GetPaginationFrom(Page page) {
        Pagination pagination = new Pagination();
        pagination.setFirst(page.isFirst());
        pagination.setLast(page.isLast());
        pagination.setNumber(page.getNumber());
        pagination.setNumberOfElements(page.getNumberOfElements());
        pagination.setSize(page.getSize());
        pagination.setTotalPages(page.getTotalPages());
        pagination.setTotalElements(page.getTotalElements());
        return pagination;
    }
}
