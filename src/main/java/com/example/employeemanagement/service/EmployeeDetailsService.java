package com.example.employeemanagement.service;

import java.util.List;
import java.util.Map;

import com.example.employeemanagement.dto.BaseEmployeeDetails;
import com.example.employeemanagement.dto.DepartmentDetailsVO;
import com.example.employeemanagement.dto.EmployeeDepartmentMappingVO;
import com.example.employeemanagement.dto.EmployeeDetailsVO;
import com.example.employeemanagement.pagination.PageArray;
import com.example.employeemanagement.pagination.PagingRequest;

public  interface EmployeeDetailsService {

	String saveEmployeeDetails(EmployeeDetailsVO modelVO);

	String saveDepartmentDetails(DepartmentDetailsVO modelVO);

	String updateEmployeeDetails(Long id, EmployeeDetailsVO request);

	String deleteDepartmentById(Long id, Map<String, String> fieldErrors);

	String updateDepartmentDetails(Long id, DepartmentDetailsVO request);

	String changeEmployeeDepartmentById(EmployeeDepartmentMappingVO modelVO);

	//Page<EmployeeDetailsVO> getAllEmployees(int start, int page, int size);

	PageArray getAllEmployees(com.example.employeemanagement.pagination.PagingRequest pagingRequest);

	PageArray getAllDepartmentList(PagingRequest pagingRequest);


	DepartmentDetailsVO getDepartmentsAndEmployees(Long departmentId);

	List<BaseEmployeeDetails> getBaseEmployeesDetails();

}
