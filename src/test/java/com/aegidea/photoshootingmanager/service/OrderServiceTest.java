package com.aegidea.photoshootingmanager.service;

import com.aegidea.photoshootingmanager.entity.Order;
import com.aegidea.photoshootingmanager.entity.User;
import com.aegidea.photoshootingmanager.enums.OrderStatus;
import com.aegidea.photoshootingmanager.enums.PhotoType;
import com.aegidea.photoshootingmanager.exception.OrderCreationException;
import com.aegidea.photoshootingmanager.repository.OrderRepository;
import com.aegidea.photoshootingmanager.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private User homer;
    
    private Order order;
    
    @Before
    public void setupClass() {
        this.homer = new User();
        this.homer.setEmail("homer@email.com");
        this.homer.setFirstName("homer");
        this.homer.setLastName("Simpson");
        this.homer.setMobileNumber("+66 666");
        
        this.order = new Order();
        this.order.setDateTime(LocalDateTime.of(2021, Month.MARCH, 4, 12, 0));
        this.order.setLogisticInfo("Some logistic infos");
        this.order.setTitle("very funny title");
        this.order.setPhotoType(PhotoType.FOOD);
        this.order.setContactData(this.homer);
        this.homer.setOrder(this.order);
    }
    
    @After
    public void tearDown() {
        this.orderRepository.deleteAll();
        this.userRepository.deleteAll();
    }
    
    @Test
    public void createNewOrderTest() {
        Order created = this.orderService.createNewOrder(this.order);
        assertEquals(OrderStatus.PENDING, created.getStatus());
        assertEquals(1, this.userRepository.count());
        assertEquals(1, this.orderRepository.count());
    }
    
    @Test(expected = OrderCreationException.class)
    public void createNewOrderInvalidDateTest() {
        this.order.setContactData(null);
        this.orderService.createNewOrder(this.order);
        
    }
    
    @Test
    public void createNewUnscheduledOrderTest() {
        
    }
    
    @Test
    public void assignOrderToPhotographerTest() {
    
    }
    
    @Test
    public void uploadOrderTest() {
        
    }
    
    @Test
    public void verifyOrderTest() {
        
    }
    
    @Test
    public void cancelOrderTest() {
        
    }
    
    @Test
    public void isDateTimeValidTest() {
        assertTrue(this.orderService.isDateTimeValid(LocalDateTime.of(2021, 10, 03, 8, 0)));
        assertTrue(this.orderService.isDateTimeValid(LocalDateTime.of(2021, 10, 03, 20, 0)));
        assertTrue(this.orderService.isDateTimeValid(LocalDateTime.of(2021, 10, 03, 12, 0)));
        assertFalse(this.orderService.isDateTimeValid(LocalDateTime.of(2021, 10, 03, 7, 59)));
        assertFalse(this.orderService.isDateTimeValid(LocalDateTime.of(2021, 10, 03, 4, 15)));
        assertFalse(this.orderService.isDateTimeValid(LocalDateTime.of(2021, 10, 03, 0, 0)));
        assertFalse(this.orderService.isDateTimeValid(LocalDateTime.of(2021, 10, 03, 23, 59)));
        assertFalse(this.orderService.isDateTimeValid(LocalDateTime.of(2021, 10, 03, 20, 01)));
        assertTrue(this.orderService.isDateTimeValid(null));
    }
    
    @Test
    public void isContactDateValidTest() {
        assertTrue(this.orderService.isContactDateValid(this.homer));
        this.homer.setEmail(null);
        assertFalse(this.orderService.isContactDateValid(this.homer));
    }
    

}
