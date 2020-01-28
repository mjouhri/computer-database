package com.excilys.cdb.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.User;
import com.excilys.cdb.persistence.UserDAO;

@Service
public class UserService implements UserDetailsService{
	
	private UserDAO userDAO;
	
	public UserService (UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	public User getUser(int id) {
		System.out.println("UserService");
		return userDAO.getComputerById(id).orElse(null);
	}
	
	public boolean newUser(User user) {
		return userDAO.newUser(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findUser(username).orElse(null);
		if (user == null) {
			throw new UsernameNotFoundException("UserName " + username + " not found");
		} else {
			return user;
		}
	}
	
}
