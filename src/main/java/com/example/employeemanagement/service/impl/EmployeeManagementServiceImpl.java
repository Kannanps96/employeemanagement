 package com.example.employeemanagement.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.employeemanagement.dto.BaseEmployeeDetails;
import com.example.employeemanagement.dto.DepartmentDetailsVO;
import com.example.employeemanagement.dto.EmployeeDepartmentMappingVO;
import com.example.employeemanagement.dto.EmployeeDetailsVO;
import com.example.employeemanagement.entities.DepartmentDetails;
import com.example.employeemanagement.entities.EmployeeDepartmentMapping;
import com.example.employeemanagement.entities.EmployeeDetails;
import com.example.employeemanagement.pagination.PageArray;
import com.example.employeemanagement.pagination.PagingRequest;
import com.example.employeemanagement.repository.DepartmentDetailsRepo;
import com.example.employeemanagement.repository.EmployeeDepartmentMappingRepo;
import com.example.employeemanagement.repository.EmployeeDetailsRepo;
import com.example.employeemanagement.service.EmployeeDetailsService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class EmployeeManagementServiceImpl implements EmployeeDetailsService {
	
	 private final ModelMapper modelMapper;
	 
	 @Autowired
	    public EmployeeManagementServiceImpl(ModelMapper modelMapper) {
	        this.modelMapper = modelMapper;
	    }
	 
	 @Autowired
	 EmployeeDetailsRepo employeeDetailsRepo;
	 
	 @Autowired
	 DepartmentDetailsRepo departmentDetailsRepo;
	 
	 @Autowired
	 EmployeeDepartmentMappingRepo employeeDepartmentMappingRepo;

	@Override
	@Transactional
	public String saveEmployeeDetails(EmployeeDetailsVO modelVO) {
		String result="N";
		try {
			EmployeeDetails employeeDetails = modelMapper.map(modelVO,EmployeeDetails.class);
			employeeDetailsRepo.save(employeeDetails);
			modelVO.setId(employeeDetails.getId());
			modelVO.setFullName(employeeDetails.getEmployeeFullName());
			result="Y";
		}catch (Exception e) {
			 log.error("Error saving employee details: {}", e.getMessage());
		}
		return result;
	}
	
	@Override
	@Transactional
	public String updateEmployeeDetails(Long id, EmployeeDetailsVO request) {
		String result="N";
		 Optional<EmployeeDetails> employee = employeeDetailsRepo.findById(id);
		 if(employee.isPresent()) {
			 EmployeeDetails employeeDetails = modelMapper.map(request,EmployeeDetails.class);
			 employeeDetails.setId(id);
			 request.setId(id);
			 employeeDetailsRepo.save(employeeDetails);
			 result="Y";
		 }

		 return result;
	}

	@Override
	@Transactional
	public String saveDepartmentDetails(DepartmentDetailsVO modelVO) {
	    String result = "N";
	    try {
	        DepartmentDetails departmentDetails = modelMapper.map(modelVO, DepartmentDetails.class);
	        departmentDetailsRepo.save(departmentDetails);
	        modelVO.setId(departmentDetails.getId());
	        result = "Y";
	    } catch (Exception e) {
	        log.error("Error saving department details: {}", e.getMessage());
	    }
	    return result;
	}

	@Override
	@Transactional
	public String deleteDepartmentById(Long departmentId,Map<String,String>fieldErrors) {
		String result="N";
		boolean hasEmployees = employeeDepartmentMappingRepo.existsByDepartmentDetailsId(departmentId);
        if (hasEmployees) {
            log.error("Cannot delete department. Employees are assigned to this department.");
            fieldErrors.put("Message", "Employees are assigned to this department.");
        }else {
             departmentDetailsRepo.deleteById(departmentId);
             result="Y";
        }
       
		return result;
	}

	@Override
	@Transactional
	public String updateDepartmentDetails(Long id, DepartmentDetailsVO request) {
		 String result="N";
		 Optional<DepartmentDetails> department = departmentDetailsRepo.findById(id);
		 if(department.isPresent()) {
			 DepartmentDetails departmentDetails = modelMapper.map(request,DepartmentDetails.class);
			 departmentDetails.setId(id);
			 request.setId(id);
			 departmentDetailsRepo.save(departmentDetails);
			 result="Y";
		 }

		 return result;
	}

	@Override
	@Transactional
	public String changeEmployeeDepartmentById(EmployeeDepartmentMappingVO modelVO) {
		String result = "N";
		if (modelVO.getDepartmentDetails() != null && modelVO.getEmployeeDetails() != null) {
			EmployeeDetails employee = employeeDetailsRepo.findById(modelVO.getEmployeeDetails().getId())
					.orElseThrow(() -> new RuntimeException("Employee not found"));
			DepartmentDetails department = departmentDetailsRepo.findById(modelVO.getDepartmentDetails().getId())
					.orElseThrow(() -> new RuntimeException("Department not found"));
			employeeDepartmentMappingRepo.deleteByEmployeeDetailsId(modelVO.getEmployeeDetails().getId());
			EmployeeDepartmentMapping employeeDepartmentMapping = new EmployeeDepartmentMapping();
			employeeDepartmentMapping.setDepartmentDetails(new DepartmentDetails());
			employeeDepartmentMapping.setEmployeeDetails(new EmployeeDetails());
			employeeDepartmentMapping.getDepartmentDetails().setId(modelVO.getDepartmentDetails().getId());
			employeeDepartmentMapping.getEmployeeDetails().setId(modelVO.getEmployeeDetails().getId());
			employeeDepartmentMapping.setDeleteStatus("N");
			employeeDepartmentMappingRepo.save(employeeDepartmentMapping);
			modelVO.setEmployeeDetails(modelMapper.map(employee, EmployeeDetailsVO.class));
			modelVO.setDepartmentDetails(modelMapper.map(department, DepartmentDetailsVO.class));
			result = "Y";
		}
		return result;
	}
	
	
	public PageArray getAllEmployees(PagingRequest pagingRequest) {
		Pageable pageRequest = PageRequest.of(pagingRequest.getStart() / pagingRequest.getLength(),
				pagingRequest.getLength());
		Page<EmployeeDetails> employeePage = employeeDetailsRepo.findAll(pageRequest);
		PageArray pageArray = new PageArray();
		pageArray.setPageNumber(employeePage.getNumber()+ 1);
        pageArray.setPageSize(employeePage.getSize());
        pageArray.setTotalElements(employeePage.getTotalElements());
        pageArray.setTotalPages(employeePage.getTotalPages());
		List<EmployeeDetailsVO> employeeResponses = employeePage.getContent().stream().map(this::mapToEmployeeResponse)
				.collect(Collectors.toList());
		pageArray.setContent(employeeResponses);
		return pageArray;
	}

	private EmployeeDetailsVO mapToEmployeeResponse(EmployeeDetails employee) {
		ModelMapper modelMapper = new ModelMapper();
		DepartmentDetailsVO departmentResponse = null;
		EmployeeDetailsVO employeeResponse = modelMapper.map(employee, EmployeeDetailsVO.class);
		Optional<EmployeeDepartmentMapping> employeeDepartment = employeeDepartmentMappingRepo
				.findByEmployeeDetailsIdAndDeleteStatus(employee.getId(),"N");
		if (employeeDepartment.isPresent()) {
			departmentResponse = modelMapper.map(employeeDepartment.get(), DepartmentDetailsVO.class);
		}
		employeeResponse.setDepartment(departmentResponse);
		return employeeResponse;
	}
	
	
	public PageArray getAllDepartmentList(PagingRequest pagingRequest) {
		Pageable pageRequest = PageRequest.of(pagingRequest.getStart() / pagingRequest.getLength(),
				pagingRequest.getLength());
		Page<DepartmentDetails> departmentPage = departmentDetailsRepo.findAll(pageRequest);
		PageArray pageArray = new PageArray();
		pageArray.setPageNumber(departmentPage.getNumber()+ 1);
        pageArray.setPageSize(departmentPage.getSize());
        pageArray.setTotalElements(departmentPage.getTotalElements());
        pageArray.setTotalPages(departmentPage.getTotalPages());
		 List<DepartmentDetailsVO> departmentResponses = departmentPage.getContent()
            .stream()
            .map(department -> modelMapper.map(department, DepartmentDetailsVO.class))
            .collect(Collectors.toList());
		pageArray.setContent(departmentResponses);
		return pageArray;
	}

	@Override
	public DepartmentDetailsVO getDepartmentsAndEmployees(Long departmentId) {
		DepartmentDetails department = departmentDetailsRepo.findByIdAndDeleteStatus(departmentId, "N");
		List<EmployeeDepartmentMapping> mappings = employeeDepartmentMappingRepo
				.findAllByDepartmentDetailsIdAndDeleteStatus(department.getId(), "N");
		List<EmployeeDetailsVO> employees = mappings.stream()
				.map(employee -> modelMapper.map(employee.getEmployeeDetails(), EmployeeDetailsVO.class))
				.collect(Collectors.toList());
		DepartmentDetailsVO departmentDetailsVO = modelMapper.map(department, DepartmentDetailsVO.class);
		departmentDetailsVO.setEmployeeList(employees);
		return departmentDetailsVO;
	}
	
	

	@Override
	public List<BaseEmployeeDetails> getBaseEmployeesDetails() {
		List<EmployeeDetails> employees = employeeDetailsRepo.findAll();
        return employees.stream()
                .map(this::mapToBaseEmployeeDetails)
                .collect(Collectors.toList());
	}
	
	
	private BaseEmployeeDetails mapToBaseEmployeeDetails(EmployeeDetails employee) {
        BaseEmployeeDetails baseEmployeeDetails = new BaseEmployeeDetails();
        baseEmployeeDetails.setId(employee.getId());
        baseEmployeeDetails.setFullName(employee.getEmployeeFullName());
        baseEmployeeDetails.setRole(employee.getRole());
        baseEmployeeDetails.setJoiningDate(new SimpleDateFormat("dd-MM-yyyy").format(employee.getJoiningDate()));
        if(employee.getReportingManager()!=null){
           baseEmployeeDetails.setReportingManager(employee.getReportingManager().getEmployeeFullName());
        }

        List<DepartmentDetails> departments = departmentDetailsRepo.findAll();
        for (DepartmentDetails department : departments) {
            if (department.getDepartmentHead() != null && department.getDepartmentHead().getId().equals(employee.getId())) {
                baseEmployeeDetails.setDepartmentName(department.getDepartmentName());
                baseEmployeeDetails.setDepartmentHead("Yes");
                return baseEmployeeDetails;
            }
        }

        Optional<EmployeeDepartmentMapping> mapping = employeeDepartmentMappingRepo.findByEmployeeDetailsIdAndDeleteStatus(employee.getId(),"N");
        if (mapping.isPresent()) {
            baseEmployeeDetails.setDepartmentName(mapping.get().getDepartmentDetails().getDepartmentName());
            baseEmployeeDetails.setDepartmentHead("No");
        }

        return baseEmployeeDetails;
    }
	
	

	

}
