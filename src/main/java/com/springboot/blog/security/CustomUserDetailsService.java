package com.springboot.blog.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.User;
import com.springboot.blog.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
				() -> new UsernameNotFoundException("User not exists with username or email: " + usernameOrEmail));

		Set<GrantedAuthority> roles = user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);

	}

}
