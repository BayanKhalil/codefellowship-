package com.example.codefellowship;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.test.web.servlet.MockMvc;




@SpringBootTest
@AutoConfigureMockMvc
class CodefellowshipApplicationTests {
	@Autowired
	ApplicationController controller;

	@Autowired
	MockMvc mockMvc;


	@Test
	public void splash() throws Exception {
		ApplicationController controller = new ApplicationController();
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("please signUP and join us")));
	}
	@Test
	public void logIn() throws Exception {
		this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Log in")));
	}
	@Test
	public void signup() throws Exception {
		this.mockMvc.perform(get("/signUp")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Sign up Page")));
	}

}
