package com.aegidea.photoshootingmanager.controller;

import com.aegidea.photoshootingmanager.configuration.Config;
import com.aegidea.photoshootingmanager.dto.OrderDTO;
import com.aegidea.photoshootingmanager.entity.Order;
import com.aegidea.photoshootingmanager.entity.Photographer;
import com.aegidea.photoshootingmanager.entity.User;
import com.aegidea.photoshootingmanager.enums.OrderStatus;
import com.aegidea.photoshootingmanager.enums.PhotoType;
import com.aegidea.photoshootingmanager.repository.OrderRepository;
import com.aegidea.photoshootingmanager.repository.PhotographerRepository;
import com.aegidea.photoshootingmanager.repository.UserRepository;
import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.given;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import java.time.LocalDateTime;
import java.time.Month;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
    
    @Autowired
    private PhotographerRepository photographerRepository;
        
    @Before
    public void setupClass() {       
        RestAssured.port = port;
    }

    @After
    public void tearDown() {
        this.userRepository.deleteAll();
        this.orderRepository.deleteAll();
        this.photographerRepository.deleteAll();
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
    
    @Test
    public void scheduleOrderTest() throws JSONException {

        User homer = new User();
        homer.setEmail("homer@email.com");
        homer.setFirstName("homer");
        homer.setLastName("Simpson");
        homer.setMobileNumber("+66 666");
        
        Order order = new Order();
        order.setLogisticInfo("Some logistic infos");
        order.setTitle("very funny title");
        order.setPhotoType(PhotoType.FOOD);
        order.setStatus(OrderStatus.UNSCHEDULED);
        homer.setOrder(order);
        
        this.orderRepository.save(order);
        
        assertEquals(1, this.orderRepository.count());
        
        JSONObject scheduleOrder = new JSONObject();
        scheduleOrder.put("dateTime", "2021-03-04T12:00:00Z");
        
        given().log().all().pathParam("orderId", order.getId()).body(scheduleOrder.toString()).contentType(ContentType.JSON).expect().when().post("/manager/orders/{orderId}").then().assertThat().statusCode(200);

        Order fromDb = this.orderRepository.findById(order.getId()).orElse(null);
        assertNotNull(fromDb.getDateTime());
        assertEquals(OrderStatus.PENDING, fromDb.getStatus()); 
        
    }
    
    @Test
    public void cancelOrderTest() throws JSONException {

        User homer = new User();
        homer.setEmail("homer@email.com");
        homer.setFirstName("homer");
        homer.setLastName("Simpson");
        homer.setMobileNumber("+66 666");
        
        Order order = new Order();
        order.setLogisticInfo("Some logistic infos");
        order.setTitle("very funny title");
        order.setPhotoType(PhotoType.FOOD);
        order.setStatus(OrderStatus.UNSCHEDULED);
        homer.setOrder(order);        
        this.orderRepository.save(order);        
        assertEquals(1, this.orderRepository.count());      
        given().log().all().pathParam("orderId", order.getId()).contentType(ContentType.JSON).expect().when().post("/manager/orders/{orderId}/cancel").then().assertThat().statusCode(200);

        Order fromDb = this.orderRepository.findById(order.getId()).orElse(null);
        assertEquals(OrderStatus.CANCEL, fromDb.getStatus()); 
        
    }
    
    @Test
    public void assignOrderTest() {
        // prepare some data
        
        // create a photographer
        Photographer ph = new Photographer();
        ph.setName("Marge");
        this.photographerRepository.save(ph);
        // create order
        User homer = new User();
        homer.setEmail("homer@email.com");
        homer.setFirstName("homer");
        homer.setLastName("Simpson");
        homer.setMobileNumber("+66 666");
        
        Order order = new Order();
        order.setLogisticInfo("Some logistic infos");
        order.setTitle("very funny title");
        order.setPhotoType(PhotoType.FOOD);
        order.setStatus(OrderStatus.UNSCHEDULED);
        homer.setOrder(order);
        
        this.orderRepository.save(order);
       

        Response response = given().log().all().pathParam("orderId", order.getId()).pathParam("photographerId", ph.getId()).contentType(ContentType.JSON).expect().when().post("/manager/orders/{orderId}/photographers/{photographerId}").then().assertThat().statusCode(200).extract().response();
        OrderDTO dto = response.as(OrderDTO.class);
        assertEquals(OrderStatus.ASSIGNED, dto.getStatus());

    
    }

}
