package com.saheb.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saheb.blog.dto.Loginrequest;
import com.saheb.blog.dto.RegisterRequest;
import com.saheb.blog.service.AuthService;
import com.saheb.blog.service.AuthenticationResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authservice;
	
	@PostMapping("/signup")
	public ResponseEntity signUp(@RequestBody RegisterRequest registerRequest) {
		authservice.signup(registerRequest);
		return new ResponseEntity(HttpStatus.OK);
	}
	
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody Loginrequest loginRequest) {
        return authservice.login(loginRequest);
    }
}
