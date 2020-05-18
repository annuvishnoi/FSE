package com.fse.pm.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
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
import com.fse.pm.entity.ParentTask;
import com.fse.pm.services.ParentTaskService;
import com.fse.pm.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(ParentTaskController.class)
public class ParentTaskControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ParentTaskService parentTaskService;
	
	@MockBean
	UserService userService;

	@InjectMocks
	ParentTaskController parentTaskController;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getAllParentTasks() throws Exception {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));

		List<ParentTask> parentTasks = new ArrayList<ParentTask>();
		parentTasks.add(parentTask);

		given(parentTaskService.getAllParentTasks()).willReturn(parentTasks);

		mockMvc.perform(get("/fse-pm-app/api/parentTasks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void getInvalidParentTaskIdTest() throws Exception {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));

		given(parentTaskService.getParentTaskByParentTaskId(20)).willReturn(parentTask);

		try {
			mockMvc.perform(get("/fse-pm-app/api/parentTasks/2000"));
		} catch (Exception e) {

		}

	}

	@Test
	public void getParentTaskByTaskIdTest() throws Exception {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));

		given(parentTaskService.getParentTaskByParentTaskId(20)).willReturn(parentTask);
		ResultActions resultActions = mockMvc.perform(get("/fse-pm-app/api/parentTasks/20"));
		MvcResult mvcResult = resultActions.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
		String response = mvcResult.getResponse().getContentAsString();

		ParentTask parentTaskReturn = objectMapper.readValue(response, ParentTask.class);
		assertEquals("Test ParentTask", parentTaskReturn.getParentTask());
		System.out.println(response);

	}

	@Test
	public void getNullParentTaskIdTest() throws Exception {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));

		given(parentTaskService.getParentTaskByParentTaskId(20)).willReturn(parentTask);
		ResultActions resultActions = mockMvc.perform(get("/fse-pm-app/api/parentTasks/null"));
		resultActions.andExpect(status().is(400));
	}

	@Test
	public void addParentTaskTest() throws Exception {
		ParentTask parentTask = new ParentTask();
		parentTask.setParentTask("Test ParentTask");
		parentTask.setParentId(Long.valueOf(20));
		doNothing().when(parentTaskService).addParentTask(parentTask);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/fse-pm-app/api/parentTasks")
			      .content(asJsonString(parentTask))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.parentId").exists());
	}

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(parentTaskController).build();

	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
