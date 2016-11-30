package com.test.cmoa.dao.i;

import java.util.List;

import com.test.cmoa.entity.Employee;

public interface EmployeeMapper {

	List<Employee> getEmpList();

	Employee getEmpById(int id);

}
