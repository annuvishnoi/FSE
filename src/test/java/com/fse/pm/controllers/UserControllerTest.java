package com.fse.pm.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.pm.entity.User;
import com.fse.pm.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	UserService userService;

	@InjectMocks
	UserController userController;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getAllUsers() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		List<User> users = new ArrayList<User>();
		users.add(user);

		given(userService.getAllUsers()).willReturn(users);

		mockMvc.perform(get("/fse-pm-app/api/users").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void getInvalidUserIdTest() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));

		given(userService.getUserByUserId(2)).willReturn(user);

		try {
			mockMvc.perform(get("/fse-pm-app/api/users/20"));
		} catch (Exception e) {

		}

	}

	@Test
	public void getUserByIdTest() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));

		given(userService.getUserByUserId(2)).willReturn(user);
		ResultActions resultActions = mockMvc.perform(get("/fse-pm-app/api/users/2"));
		MvcResult mvcResult = resultActions.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
		String response = mvcResult.getResponse().getContentAsString();

		User userReturn = objectMapper.readValue(response, User.class);
		assertEquals("First_Check", userReturn.getFirstName());
		System.out.println(response);

	}

	@Test
	public void getNullUserIdTest() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));

		given(userService.getUserByUserId(2)).willReturn(user);
		ResultActions resultActions = mockMvc.perform(get("/fse-pm-app/api/users/null"));
		resultActions.andExpect(status().is(400));
	}

	@Test
	public void addUserTest() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		doNothing().when(userService).addUser(user);
		
		
		given(userService.getUserByEmployeeId(user.getEmployeeId())).willReturn(null);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/fse-pm-app/api/users")
			      .content(asJsonString(user))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists());
	}
	@Test
	public void updateUserTest() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));
		doNothing().when(userService).updateUser(user);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .put("/fse-pm-app/api/users")
			      .content(asJsonString(user))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.userId").exists());
	}
	
	@Test
    public void deleteUserTest() throws Exception {
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		user.setUserId(Long.valueOf(2));

        doNothing().when(userService).deleteUser(user.getUserId());

        mockMvc.perform(
                delete("/fse-pm-app/api/users/{userId}", user.getUserId()))
                .andExpect(status().isOk());

        verify(userService, times(1)).deleteUser(user.getUserId());
        verifyNoMoreInteractions(userService);
    }


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
