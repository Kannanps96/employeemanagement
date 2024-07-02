package com.example.employeemanagement.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PagingRequest {

	    private int start;
	    private int length=20; //default length 20

}