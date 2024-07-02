package com.example.employeemanagement.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DepartmentDetailsVO {
	
    private Long id;
    private String departmentName;
    private Date creationDate;
    private EmployeeDetailsVO departmentHead;
    private List<EmployeeDetailsVO> employeeList;
    private String deleteStatus="N";
	

}
