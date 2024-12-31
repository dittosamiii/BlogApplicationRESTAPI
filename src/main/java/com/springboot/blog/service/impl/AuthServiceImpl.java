package com.springboot.blog.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider tokenProvider;

	@Override
	public String login(LoginDto loginDto) {
		Authentication authenticate = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = tokenProvider.generateToken(authenticate);
		return token;
	}

	@Override
	public String register(RegisterDto registerDto) {
		// add check for user name already exists
		if (userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogAPIException("Username already exists!");
		}

		// add check for email already exists
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIException("Email already exists!");
		}

		User user = new User();
		user.setName(registerDto.getName());
		user.setEmail(registerDto.getEmail());
		user.setUsername(registerDto.getUsername());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role role = roleRepository.findByName("ROLE_USER").get();
		roles.add(role);
		user.setRoles(roles);

		userRepository.save(user);
		return "User registered successfully!";
	}

}
