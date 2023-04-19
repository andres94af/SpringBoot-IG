package com.instagram;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class InstagramSpringBootApplicationTests {

	@Test
	void contextLoads() {
		PasswordEncoder passEncoder = new BCryptPasswordEncoder();
		System.out.println(passEncoder.encode("12345"));
	}

}
