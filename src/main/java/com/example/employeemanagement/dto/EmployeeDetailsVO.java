package com.example.employeemanagement.dto;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeeDetailsVO {
    private Long id;
	private String titleName;
	private String firstName;
	private String lastName;
	private String fullName;
    private Date dateOfBirth;
    private Double salary;
    private String address;
    private String role;
    private Date joiningDate;
    private Double yearlyBonusPercentage;
    private EmployeeDetailsVO reportingManager;
    private DepartmentDetailsVO department;

}
