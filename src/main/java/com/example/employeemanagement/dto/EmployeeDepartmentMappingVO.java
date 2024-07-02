package com.example.employeemanagement.dto;

import lombok.Data;

@Data
public class EmployeeDepartmentMappingVO {
	
    private Long id;
    private EmployeeDetailsVO employeeDetails;
    private DepartmentDetailsVO departmentDetails;
    private String deleteStatus;
	

}
