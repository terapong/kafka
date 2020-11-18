package toto.kafka.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import toto.kafka.springboot.entity.Employee;
import toto.kafka.springboot.exception.ResourceNotFoundException;
import toto.kafka.springboot.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeRepository repo;

	// get all Employees
	@GetMapping
	public List<Employee> getAllEmployees() {
		return this.repo.findAll();
	}

	// get Employee by id
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable (value = "id") long id) {
		return this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :" + id));
	}

	// create Employee
	@PostMapping
	public Employee createEmployee(@RequestBody Employee newObj) {
		return this.repo.save(newObj);
	}
	
	// update Employee
	@PutMapping("/{id}")
	public Employee updateEmployee(@RequestBody Employee newObj, @PathVariable ("id") long id) {
		 Employee existing = this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :" + id));
		 existing.setFirstName(newObj.getFirstName());
		 existing.setLastName(newObj.getLastName());
		 existing.setEmail(newObj.getEmail());
		 return this.repo.save(existing);
	}
	
	// delete Employee by id
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable ("id") long id){
		 Employee existing = this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id :" + id));
		 this.repo.delete(existing);
		 return ResponseEntity.ok().build();
	}
}
