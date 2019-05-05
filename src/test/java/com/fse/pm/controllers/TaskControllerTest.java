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
import com.fse.pm.entity.Task;
import com.fse.pm.entity.User;
import com.fse.pm.services.TaskService;
import com.fse.pm.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	TaskService taskService;
	
	@MockBean
	UserService userService;

	@InjectMocks
	TaskController taskController;

	@Autowired
	private ObjectMapper objectMapper;

	//@Test
	public void getAllTasks() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);

		given(taskService.getAllTasks()).willReturn(tasks);

		mockMvc.perform(get("/api/tasks").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	//@Test
	public void getInvalidTaskIdTest() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		given(taskService.getTaskByTaskId(20)).willReturn(task);

		try {
			mockMvc.perform(get("/api/tasks/2000"));
		} catch (Exception e) {

		}

	}

	//@Test
	public void getTaskByTaskIdTest() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		given(taskService.getTaskByTaskId(20)).willReturn(task);
		ResultActions resultActions = mockMvc.perform(get("/api/tasks/20"));
		MvcResult mvcResult = resultActions.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
		String response = mvcResult.getResponse().getContentAsString();

		Task taskReturn = objectMapper.readValue(response, Task.class);
		assertEquals("Test Task", taskReturn.getTask());
		System.out.println(response);

	}

	//@Test
	public void getNullTaskIdTest() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

		given(taskService.getTaskByTaskId(20)).willReturn(task);
		ResultActions resultActions = mockMvc.perform(get("/api/tasks/null"));
		resultActions.andExpect(status().is(400));
	}

	@Test
	public void addTaskTest() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));
		
		User user = new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		task.setSelectedEmployee(user);
		
		doNothing().when(taskService).addTask(task);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/tasks")
			      .content(asJsonString(task))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.taskId").exists());
	}
	//@Test
	public void updateTaskTest() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));
		doNothing().when(taskService).updateTask(task);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .put("/api/tasks")
			      .content(asJsonString(task))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.taskId").exists());
	}
	
	//@Test
    public void deleteTaskTest() throws Exception {
		Task task = new Task();
		task.setTask("Test Task");
		task.setTaskId(Long.valueOf(20));

        doNothing().when(taskService).deleteTask(task.getTaskId());

        mockMvc.perform(
                delete("/api/tasks/{taskId}", task.getTaskId()))
                .andExpect(status().isOk());

        verify(taskService, times(1)).deleteTask(task.getTaskId());
        verifyNoMoreInteractions(taskService);
    }


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
