package com.excilys.cdb.controller;


import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.configuration.JwtTokenUtil;
import com.excilys.cdb.exeception.AuthenticationException;
import com.excilys.cdb.model.User;
import com.excilys.cdb.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	private JwtTokenUtil jwtTokenUtil;
	private UserService userService;
	private AuthenticationManager authenticationManager;

	
	public UserController(UserService userService, AuthenticationManager authenticationManager,
			JwtTokenUtil jwtTokenUtil) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtil = jwtTokenUtil;
	}
	
	@CrossOrigin
	@GetMapping
	public User getUser() {
		System.out.println("UserController");
		return userService.getUser(1);
	}
	
	
	@CrossOrigin
	@PostMapping
	public ResponseEntity<?> login(@RequestBody User authentificationRequest) throws AuthenticationException {
		authenticate(authentificationRequest.getUsername(), authentificationRequest.getPassword());
		final UserDetails userDetails = userService.loadUserByUsername(authentificationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(token);
	}

	@ExceptionHandler({ AuthenticationException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("INVALID_CREDENTIALS", e);
		}
	}
	
	
	
}
