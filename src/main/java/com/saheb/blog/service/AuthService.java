package com.saheb.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.saheb.blog.dto.Loginrequest;
import com.saheb.blog.dto.RegisterRequest;
import com.saheb.blog.post.User;
import com.saheb.blog.repository.UserRepository;
import com.saheb.blog.security.JwtProvider;

@Service
public class AuthService {
	
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

	public void signup(RegisterRequest registerRequest) {
		
		User user=new User();
		user.setMail(registerRequest.getEmail());
		//user.setPassword(encodePassword(registerRequest.getPassword()));
		user.setUserName(registerRequest.getUserName());
		userRepository.save(user);
	}

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

	
	public AuthenticationResponse login(Loginrequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }

    public Optional<org.springframework.security.core.userdetails.User> getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return Optional.of(principal);
    }

}
