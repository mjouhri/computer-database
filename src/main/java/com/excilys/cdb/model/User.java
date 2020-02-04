package com.excilys.cdb.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	@Column
	private String username;
	@Column
	private String password;


	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	private User(UserBuilder builder) {
		this.id = builder.id;
		this.username = builder.username;
		this.password = builder.password;
	}
	
	public static class UserBuilder {
		private int id;
		private String username;
		private String password;

		public UserBuilder() {
		}

		public UserBuilder idUser(int id) {
			this.id = id;
			return this;
		}

		public UserBuilder usernameUser(String username) {
			this.username = username;
			return this;
		}
		
		public UserBuilder passwordUser(String password) {
			this.password = password;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}
	
	
	

}
