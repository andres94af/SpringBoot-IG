package com.instagram.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
class UserDetailsServiceImpl implements UserDetailsService {
	
	private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	return User.builder().username("usuario@mail.com").password(encoder.encode("AnDrEs123")).roles("USER").build();
    }

}