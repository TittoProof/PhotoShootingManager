package com.aegidea.photoshootingmanager.controller;

import com.aegidea.photoshootingmanager.configuration.Config;
import com.aegidea.photoshootingmanager.dto.OrderDTO;
import com.aegidea.photoshootingmanager.enums.OrderStatus;
import com.aegidea.photoshootingmanager.enums.PhotoType;
import com.aegidea.photoshootingmanager.repository.OrderRepository;
import com.aegidea.photoshootingmanager.repository.UserRepository;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.authentication.PreemptiveBasicAuthScheme;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Config.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderControllerEndToEndTest {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
        
    @Before
    public void setupClass() {       
        RestAssured.port = port;
    }

    @After
    public void tearDown() {
        this.userRepository.deleteAll();
        this.orderRepository.deleteAll();
    }
    
    @Test
    public void createOrderTest() throws JSONException {
        
        JSONObject contactData = new JSONObject();

        JSONObject newOrder = new JSONObject();
        contactData.put("firstName", "Lisa");
        contactData.put("lastName", "Simpson");
        contactData.put("email", "lisa@email.com");
        contactData.put("mobileNumber", "+39666");
        
        newOrder.put("contactData", contactData);
        newOrder.put("photoType", PhotoType.FOOD);
        newOrder.put("title", "a new photoset");

        Response response = given().log().all().body(newOrder.toString()).contentType(ContentType.JSON).expect().when().post("/manager/orders").then().assertThat().statusCode(201).extract().response();
        OrderDTO dto = response.as(OrderDTO.class);
        assertEquals(PhotoType.FOOD, dto.getPhotoType());
        assertEquals(OrderStatus.UNSCHEDULED, dto.getStatus());
        assertEquals("Lisa", dto.getContactData().getFirstName());
    }
    
    @Test
    public void createOrderIncompleteInformationTest() throws JSONException {
        
        JSONObject contactData = new JSONObject();

        JSONObject newOrder = new JSONObject();
        contactData.put("lastName", "Simpson");
        contactData.put("email", "lisa@email.com");
        contactData.put("mobileNumber", "+39666");
        
        newOrder.put("contactData", contactData);
        newOrder.put("photoType", PhotoType.FOOD);
        newOrder.put("title", "a new photoset");

        given().log().all().body(newOrder.toString()).contentType(ContentType.JSON).expect().when().post("/manager/orders").then().assertThat().statusCode(400);

    }

}
