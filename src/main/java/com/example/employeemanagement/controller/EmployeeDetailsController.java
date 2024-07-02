package com.example.employeemanagement.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.employeemanagement.dto.BaseEmployeeDetails;
import com.example.employeemanagement.dto.DepartmentDetailsVO;
import com.example.employeemanagement.dto.EmployeeDepartmentMappingVO;
import com.example.employeemanagement.dto.EmployeeDetailsVO;
import com.example.employeemanagement.dto.ResultVO;
import com.example.employeemanagement.pagination.PageArray;
import com.example.employeemanagement.pagination.PagingRequest;
import com.example.employeemanagement.service.EmployeeDetailsService;

@RestController
@RequestMapping("/employee")
public class EmployeeDetailsController {
	
	@Autowired
	EmployeeDetailsService employeeDetailsService;
	
	
	@PostMapping(value = "/saveEmployeeDetails")
	@ResponseBody
	public ResponseEntity<ResultVO> saveEmployee(@RequestBody  EmployeeDetailsVO modelVO)
			throws IllegalAccessException, InvocationTargetException {
		String result = "N";
		ResultVO resultVO = new ResultVO();
		try {
			result = employeeDetailsService.saveEmployeeDetails(modelVO);
		} catch (Exception e) {
		}
		resultVO.setResult(result);
		resultVO.setResultObject(modelVO);
		if (result != null && result.equals("Y")) {
			Map<String , String> filedErrors=new HashMap<>();
			filedErrors.put("Successfull","Employee Details Saved Successfully");
			resultVO.setFieldErrors(filedErrors);
			return new ResponseEntity<>(resultVO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	
	@PutMapping("/updateEmployeeDetails/{id}")
    public ResponseEntity<ResultVO> updateEmployeeDetails(@PathVariable Long id, @RequestBody EmployeeDetailsVO request) {
		String result = "N";
        ResultVO resultVO = new ResultVO();
        result = employeeDetailsService.updateEmployeeDetails(id,request);
        resultVO.setResult(result);
		resultVO.setResultObject(request);
		if (result != null && result.equals("Y")) {
			Map<String , String> filedErrors=new HashMap<>();
			filedErrors.put("Successfull","Employee Updated Saved Successfully");
			resultVO.setFieldErrors(filedErrors);
			return new ResponseEntity<>(resultVO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
	
	
	@PostMapping(value = "/saveDepartmentDetails")
	@ResponseBody
	public ResponseEntity<ResultVO> saveDepartmentDetails(@RequestBody  DepartmentDetailsVO modelVO)
			throws IllegalAccessException, InvocationTargetException {
		String result = "N";
		ResultVO resultVO = new ResultVO();
		Map<String , String> filedErrors=new HashMap<>();
		try {
			result = employeeDetailsService.saveDepartmentDetails(modelVO);
		} catch (Exception e) {
		}
		resultVO.setResult(result);
		resultVO.setResultObject(modelVO);
		if (result != null && result.equals("Y")) {
			filedErrors.put("Successfull","Department Details Saved Successfully");
			resultVO.setFieldErrors(filedErrors);
			return new ResponseEntity<>(resultVO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	
	
	@PostMapping(value="/deleteDepartment")
	@ResponseBody
    public ResponseEntity<ResultVO> deleteDepartment(@RequestBody  DepartmentDetailsVO modelVO) {
        ResultVO resultVO = new ResultVO();
        String result="N";
        Map<String,String> fieldErrors=new LinkedHashMap<>();
        result=employeeDetailsService.deleteDepartmentById(modelVO.getId(),fieldErrors);
        if(result=="N") {
            fieldErrors.put("Delete Unsuccessful","Delete Unsuccessful");
            resultVO.setFieldErrors(fieldErrors);
        }
        resultVO.setResult(result);
        return new ResponseEntity<>(resultVO, HttpStatus.OK);
    }
	
	
	@PutMapping("/updateDepartmentDetails/{id}")
    public ResponseEntity<ResultVO> updateDepartmentDetails(@PathVariable Long id, @RequestBody DepartmentDetailsVO request) {
		String result = "N";
        ResultVO resultVO = new ResultVO();
        result = employeeDetailsService.updateDepartmentDetails(id,request);
        resultVO.setResult(result);
		resultVO.setResultObject(request);
		if (result != null && result.equals("Y")) {
			Map<String , String> filedErrors=new HashMap<>();
			filedErrors.put("Successfull","Department Updated Saved Successfully");
			resultVO.setFieldErrors(filedErrors);
			return new ResponseEntity<>(resultVO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
	
	
	@PutMapping("/changeEmployeeDepartment")
    public ResponseEntity<ResultVO> changeEmployeeDepartment(@RequestBody EmployeeDepartmentMappingVO modelVO) {
		String result = "N";
        ResultVO resultVO = new ResultVO();
        result = employeeDetailsService.changeEmployeeDepartmentById(modelVO);
        resultVO.setResult(result);
		resultVO.setResultObject(modelVO);
		if (result != null && result.equals("Y")) {
			Map<String , String> filedErrors=new HashMap<>();
			filedErrors.put("Successfull","Employee Department Changed Successfully");
			resultVO.setFieldErrors(filedErrors);
			return new ResponseEntity<>(resultVO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
    }
	
	
	@GetMapping("/getAllEmployeeList")
    public ResponseEntity<PageArray> getAllEmployees(@RequestBody(required = false) PagingRequest pagingRequest) {
		PageArray employeePage = employeeDetailsService.getAllEmployees(pagingRequest);
        return new ResponseEntity<>(employeePage, HttpStatus.OK);
    }
	
	
	@GetMapping("/getAllDepartmentList")
    public ResponseEntity<PageArray> getAllDepartmentList(@RequestBody(required = false) PagingRequest pagingRequest) {
		PageArray employeePage = employeeDetailsService.getAllDepartmentList(pagingRequest);
        return new ResponseEntity<>(employeePage, HttpStatus.OK);
    }
	
	
	@GetMapping("/getAllDepartmentAndEmployees")
    public ResponseEntity<DepartmentDetailsVO> getDepartmentAndEmployees(@RequestParam(name = "departmentId", required = true) Long departmentId,@RequestParam(name = "expand", required = false) String expand) {
		DepartmentDetailsVO department=null;
		if ("employee".equals(expand)) {
	         department = employeeDetailsService.getDepartmentsAndEmployees(departmentId);
	         return ResponseEntity.ok(department);
	    }else {
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
		
    }
	
	
	@GetMapping("/getAllBaseEmployeesDetails")
    public ResponseEntity<List<BaseEmployeeDetails>> getAllBaseEmployeesDetails(@RequestParam(name = "lookup", required = true, defaultValue = "false") boolean lookup) {
		List<BaseEmployeeDetails> baseEmployeeDetails=new ArrayList<>();
		if(lookup) {
			baseEmployeeDetails = employeeDetailsService.getBaseEmployeesDetails();
		}
	    return ResponseEntity.ok(baseEmployeeDetails);
		
    }
	
	
	
	
	
	
}

