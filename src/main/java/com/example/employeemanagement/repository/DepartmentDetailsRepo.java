package com.example.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.employeemanagement.entities.DepartmentDetails;


public interface DepartmentDetailsRepo extends JpaRepository<DepartmentDetails, Long> {


	DepartmentDetails findByIdAndDeleteStatus(Long departmentId, String string);

	

}
