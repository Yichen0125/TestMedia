package com.test.cmoa.entity;

public class Employee {
	private Integer id;
	private String empName;
	public Employee(Integer id, String empName) {
		super();
		this.id = id;
		this.empName = empName;
	}
	public Employee() {
		super();
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", empName=" + empName + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	
}
