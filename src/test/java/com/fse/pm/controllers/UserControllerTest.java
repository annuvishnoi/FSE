package com.fse.pm.controllers;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fse.pm.entity.User;
import com.jayway.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource(value= {"classpath:application.properties"})
public class UserControllerTest {

	@Value("${server.port}")
	Integer port;
	
	@Value("${server.servlet.context-path}")
	String contextPath;
	
	@Test
	public void getAllUsers() {
		given().when().get("/api/users").then().statusCode(200);
	}
	@Test
	public void getUserByIdTest() {
		given().when().get("/api/users/10").then().statusCode(200);
	}

	@Test
	public void addUserTest() {
		User user=new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		given()
			.contentType("application/json")
			.body(user)
			.when().post("/api/users").then().statusCode(200)
			.and()
			.contentType("application/json")
			.and()
			.body("firstName", equalTo(user.getFirstName()));
	}
	@Test
	public void deleteUserTest() {
		User user=new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		given()
			.contentType("application/json")
			.when().delete("/api/users/2").then().statusCode(200)
			.and()
			.contentType("application/json");
	}
	@Test
	public void editUserTest() {
		User user=new User();
		user.setFirstName("First_Check");
		user.setLastName("Last_Check");
		user.setEmployeeId(Long.valueOf(350044));
		given()
			.contentType("application/json")
			.body(user)
			.when().put("/api/users").then().statusCode(200)
			.and()
			.contentType("application/json")
			.and()
			.body("firstName", equalTo(user.getFirstName()));
	}
	@Before
	public void setBaseUri() {
		/*RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";*/
		if(port == null)
			RestAssured.port= Integer.valueOf(8080);
		else
			RestAssured.port= Integer.valueOf(port);
		
		if(contextPath == null)
			contextPath="/fse-pm-app/";
		RestAssured.basePath=contextPath;
		
		String host="http://localhost";
		RestAssured.baseURI = host;
	}
}
