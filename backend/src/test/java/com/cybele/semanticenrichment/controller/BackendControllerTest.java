package com.cybele.semanticenrichment.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.cybele.semanticenrichment.SpringBootVuejsApplication;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/*@RunWith(SpringRunner.class)
@SpringBootTest(
		classes = SpringBootVuejsApplication.class,
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ExtendWith(SpringExtension.class)*/
public class BackendControllerTest {
/*
	@LocalServerPort
	private int port;

	@Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }
	

	@Test
    public void addNewDatasetAndRetrieveItBack() throws JSONException {
		    
	    JSONObject requestParams = new JSONObject();
        requestParams.put("label", "dataset1"); // Cast
        requestParams.put("language", "english");	          
       
        given()           
        		.accept(ContentType.JSON)
        		.contentType(ContentType.JSON)
        		.body(requestParams.toString())
            .when()
                .post("/api/dataset")
            .then()
                .statusCode(is(HttpStatus.SC_CREATED))
                .assertThat()
                	.body(is(notNullValue()));            
	}	*/
}