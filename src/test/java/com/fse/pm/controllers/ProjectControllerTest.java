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
import com.fse.pm.entity.Project;
import com.fse.pm.services.ProjectService;
import com.fse.pm.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	ProjectService projectService;
	
	@MockBean
	UserService userService;

	@InjectMocks
	ProjectController projectController;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void getAllProjects() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		List<Project> projects = new ArrayList<Project>();
		projects.add(project);

		given(projectService.getAllProjects()).willReturn(projects);

		mockMvc.perform(get("/api/projects").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void getInvalidProjectIdTest() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		given(projectService.getProjectByProjectId(20)).willReturn(project);

		try {
			mockMvc.perform(get("/api/projects/2000"));
		} catch (Exception e) {

		}

	}

	@Test
	public void getProjectByProjectIdTest() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		given(projectService.getProjectByProjectId(20)).willReturn(project);
		ResultActions resultActions = mockMvc.perform(get("/api/projects/20"));
		MvcResult mvcResult = resultActions.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
		String response = mvcResult.getResponse().getContentAsString();

		Project projectReturn = objectMapper.readValue(response, Project.class);
		assertEquals("Test Project", projectReturn.getProject());
		System.out.println(response);

	}

	@Test
	public void getNullProjectIdTest() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

		given(projectService.getProjectByProjectId(20)).willReturn(project);
		ResultActions resultActions = mockMvc.perform(get("/api/projects/null"));
		resultActions.andExpect(status().is(400));
	}

	@Test
	public void addProjectTest() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));
		doNothing().when(projectService).addProject(project);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .post("/api/projects")
			      .content(asJsonString(project))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.projectId").exists());
	}
	@Test
	public void updateProjectTest() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));
		doNothing().when(projectService).updateProject(project);
		
		mockMvc.perform( MockMvcRequestBuilders
			      .put("/api/projects")
			      .content(asJsonString(project))
			      .contentType(MediaType.APPLICATION_JSON)
			      .accept(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk())
			      .andExpect(MockMvcResultMatchers.jsonPath("$.projectId").exists());
	}
	
	@Test
    public void deleteProjectTest() throws Exception {
		Project project = new Project();
		project.setProject("Test Project");
		project.setProjectId(Long.valueOf(20));

        doNothing().when(projectService).deleteProject(project.getProjectId());

        mockMvc.perform(
                delete("/api/projects/{projectId}", project.getProjectId()))
                .andExpect(status().isOk());

        verify(projectService, times(1)).deleteProject(project.getProjectId());
        verifyNoMoreInteractions(projectService);
    }


	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();

	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
