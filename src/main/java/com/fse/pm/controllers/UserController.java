package com.fse.pm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.fse.pm.exceptions.BadRequestException;
import com.fse.pm.exceptions.NotFoundException;
import com.fse.pm.services.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	@Autowired
	UserService userService;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/users")
	public List<User> getUsers(){
		logger.info("start getUsers method>>");
		List<User> users = this.userService.getAllUsers();
		if(users==null || users.isEmpty()) {
			logger.info("User Records not Found!!!");
			throw new NotFoundException("User Records not Found!!!");
		}
		logger.info("end getUsers method>>");
		return users;
	}
	
	@GetMapping("/users/managers")
	public List<User> getManagers(){
		logger.info("start getManagers method>>");
		List<User> users = this.userService.getAllManagers();
		if(users==null || users.isEmpty()) {
			logger.info("User Records not Found!!!");
			throw new NotFoundException("User Records not Found!!!");
		}
		logger.info("end getManagers method>>");
		return users;
	}
	
	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable Long userId){
		logger.info("start getUser method>>" + userId);
		User user = this.userService.getUserByUserId(userId);
		if(user==null) {
			logger.info("User id not found - " + userId);
			
			throw new NotFoundException("User id not found - " + userId);
		}
		logger.info("end getUser method>>");
		return user;
	}
	
	@PostMapping("/users")
	public User addUser(@RequestBody User user){
		
		logger.info("start addUser method>>");
		if(user == null) {
			logger.info("Cannot add new User!!!");
			throw new BadRequestException("User Can not be null!!!");
		}
		
		List<User> users = this.userService.getUserByEmployeeId(user.getEmployeeId());
		if(users != null && users.size() > 0) {
			logger.info("Duplicate Employee Id!!");
			throw new BadRequestException("Duplicate Employee Id!!");
		}
		this.userService.addUser(user);
		logger.info("end addUser method>>");
		return user;
	}
	
	@PutMapping("/users")
	public User editUser(@RequestBody User user){
		logger.info("start editUser method>>");
		if(user == null) {
			logger.info("Cannot update User!!!");
			throw new BadRequestException("User Can not be null!!!");
		}
		this.userService.updateUser(user);
		logger.info("end editUser method>>");
		
		return user;
	}
	@DeleteMapping("/users/{userId}")
	public boolean deleteUser(@PathVariable Long userId){
		logger.info("start deleteUser method>>");
		if(userId == null) {
			logger.info("Cannot delete User!!!");
			throw new BadRequestException("User Id Can not be null!!!");
		}
		this.userService.deleteUser(userId);
		logger.info("start deleteUser method>>");
		return true;
	
	}
}

