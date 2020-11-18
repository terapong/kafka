package toto.kafka.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import toto.kafka.springboot.entity.User;
import toto.kafka.springboot.exception.ResourceNotFoundException;
import toto.kafka.springboot.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository repo;

	// get all users
	@GetMapping
	public List<User> getAllUsers() {
		return this.repo.findAll();
	}

	// get user by id
	@GetMapping("/{id}")
	public User getUserById(@PathVariable (value = "id") long id) {
		return this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + id));
	}

	// create user
	@PostMapping
	public User createUser(@RequestBody User newObj) {
		return this.repo.save(newObj);
	}
	
	// update user
	@PutMapping("/{id}")
	public User updateUser(@RequestBody User newObj, @PathVariable ("id") long id) {
		 User existing = this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + id));
		 existing.setFirstName(newObj.getFirstName());
		 existing.setLastName(newObj.getLastName());
		 existing.setEmail(newObj.getEmail());
		 return this.repo.save(existing);
	}
	
	// delete user by id
	@DeleteMapping("/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable ("id") long id){
		 User existing = this.repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id :" + id));
		 this.repo.delete(existing);
		 return ResponseEntity.ok().build();
	}
}
