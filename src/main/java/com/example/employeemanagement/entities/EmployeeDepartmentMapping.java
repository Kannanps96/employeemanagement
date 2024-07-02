package com.example.employeemanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(schema = "employeemanagement", catalog = "employeemanagement", name = "employee_department_mapping")
@Data
public class EmployeeDepartmentMapping {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	@ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeDetails employeeDetails;
	
	@ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentDetails departmentDetails;
	
	@Column(name = "delete_status")
    private String deleteStatus;
	

}
