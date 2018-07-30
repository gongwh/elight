package com.snow.lib.result;

import lombok.Data;

/**
 * Created by SNOW on 2018.01.30.
 */
@Data
public class Pagination {
    private boolean first;          // 是否值开头页
    private boolean last;           // 是否最后一页
    private int totalPages;         // 所有页数
    private long totalElements;     // 所有个数
    private int size;               // 每页大小
    private int pageNumber;             // 当前页数
    private int numberOfElements;   // 当前页数量
}
