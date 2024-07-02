package com.example.employeemanagement.dto;

import lombok.Data;

@Data
public class BaseEmployeeDetails {
	
    private Long id;
    private String fullName;
    private String role;
    private String joiningDate;
    private String reportingManager;
    private String departmentName;
    private String departmentHead;

}
