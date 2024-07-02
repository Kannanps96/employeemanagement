package com.example.employeemanagement.pagination;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageArray{
    
    private List<?> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}