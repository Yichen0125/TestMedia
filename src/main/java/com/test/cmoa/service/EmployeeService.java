package com.test.cmoa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.cmoa.dao.i.EmployeeMapper;
import com.test.cmoa.entity.Employee;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeMapper employeeMapper;

	public List<Employee> getEmpList() {
		return employeeMapper.getEmpList();
	}

	public Employee getEmpById(Integer id) {
		return employeeMapper.getEmpById(id);
	}
	
	
}
