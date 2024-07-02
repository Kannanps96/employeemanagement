package com.example.employeemanagement.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(schema = "employeemanagement", catalog = "employeemanagement", name = "department_details")
@Data
public class DepartmentDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
    
	@Column(name = "department_name")
    private String departmentName;
    
	@Column(name = "creation_date")
    private Date creationDate;
	
	@Column(name = "delete_status")
    private String deleteStatus;
	
	@OneToOne
    @JoinColumn(name = "department_head_id")
    private EmployeeDetails departmentHead;

}
