package com.example.employeemanagement.dto;

import java.util.Map;

import lombok.Data;

@Data
public class ResultVO {
	private String result;
	private Map<String, String> fieldErrors;
	private Object resultObject;

}