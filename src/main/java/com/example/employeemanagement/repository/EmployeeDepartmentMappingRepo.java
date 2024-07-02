package com.example.employeemanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.employeemanagement.entities.EmployeeDepartmentMapping;
import com.example.employeemanagement.entities.EmployeeDetails;

public interface EmployeeDepartmentMappingRepo extends JpaRepository<EmployeeDepartmentMapping, Long> {

	boolean existsByDepartmentDetailsId(Long departmentId);

	
	@Modifying
	@Query("UPDATE EmployeeDepartmentMapping as g set g.deleteStatus='Y' WHERE g.employeeDetails.id=?1")
	void deleteByEmployeeDetailsId(Long id);


	Optional<EmployeeDepartmentMapping> findByEmployeeDetailsIdAndDeleteStatus(Long id, String deleteStatus);



	List<EmployeeDepartmentMapping> findAllByDepartmentDetailsIdAndDeleteStatus(Long id, String string);



	

}
