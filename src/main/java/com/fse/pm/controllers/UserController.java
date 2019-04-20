package com.fse.pm.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.pm.entity.User;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	@Autowired
	//BookService bookService;
	

	@GetMapping("/users")
	public List<User> getUsers(){
		
		return new ArrayList<User>();
	}
	
	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable Long userId){
				
		return new User();
	}
	
	
	
	@PostMapping("/users")
	public User addUser(@RequestBody User user){
		
		
		return user;
	}
	
	@PutMapping("/users")
	public User editUser(@RequestBody User user){
		
		return user;
	}
	@DeleteMapping("/users/{userId}")
	public boolean deleteUser(@PathVariable Long userId){

		return true;
	}
}
