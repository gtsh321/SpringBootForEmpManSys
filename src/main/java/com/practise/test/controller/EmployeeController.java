package com.practise.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practise.test.exception.ResourceNotFoundException;
import com.practise.test.model.Employee;
import com.practise.test.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {
    
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees rest api
    
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees")
	public List<Employee> getAllEmployees()
	{
		return employeeRepository.findAll();
		
	}
	
	//add employee rest api
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/createemployees")
	public Employee createEmployee(@RequestBody Employee employee)
	{
		return employeeRepository.save(employee);
	}
	
	//get employee by id rest api 
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id)
	{
		Employee employee = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee not exist with id : "+ id));
		return ResponseEntity.ok(employee);
	}
	
	//update employee rest api 
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee)
	{
		Employee em = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee not exist with id : "+ id));
		em.setFirstName(employee.getFirstName());
		em.setLastName(employee.getLastName());
		em.setEmailId(employee.getEmailId());
		Employee updatedEmployee = employeeRepository.save(em);
		return ResponseEntity.ok(updatedEmployee);
	}
	
	//delete employee rest api 
	
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id)
	{
		Employee em = employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("employee not exist with id : "+ id));
		employeeRepository.delete(em);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
	
}
