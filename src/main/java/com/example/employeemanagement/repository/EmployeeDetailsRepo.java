package com.example.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeemanagement.entities.EmployeeDetails;

public interface EmployeeDetailsRepo extends JpaRepository<EmployeeDetails, Long> {

	

}
