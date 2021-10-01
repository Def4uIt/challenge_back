package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<User> list() {
		return userRepository.findAll();
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public User create(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@DeleteMapping("/{id}")
	public HttpStatus remove(@PathVariable("id") long id) {
		Optional<User> user = userRepository.findById(id);
	
		if(user.isPresent()) {
	    try {
			userRepository.deleteById(id);
	        return HttpStatus.NO_CONTENT;
	      } catch (Exception e) {
	        return HttpStatus.INTERNAL_SERVER_ERROR;
	      }
		} else {
			return HttpStatus.NOT_FOUND;
		}
	}
	
	  @PutMapping("/{id}")
	  public HttpStatus update(@PathVariable("id") long id, @RequestBody User user) {
	    Optional<User> userData = userRepository.findById(id);
	
	    if (userData.isPresent()) {
	      User _user = userData.get();
	      _user.setName(user.getName());
	      _user.setEmail(user.getEmail());
	      userRepository.save(_user);
	      return HttpStatus.OK;
	    } else {
	      return HttpStatus.NOT_FOUND;
	    }
	  }
}
