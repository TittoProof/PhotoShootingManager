package com.aegidea.photoshootingmanager.service;

import com.aegidea.photoshootingmanager.entity.Order;
import com.aegidea.photoshootingmanager.entity.Photographer;
import com.aegidea.photoshootingmanager.entity.User;
import com.aegidea.photoshootingmanager.enums.OrderStatus;
import com.aegidea.photoshootingmanager.enums.PhotoType;
import com.aegidea.photoshootingmanager.exception.AssignOrderException;
import com.aegidea.photoshootingmanager.exception.OrderCancelledException;
import com.aegidea.photoshootingmanager.exception.OrderCreationException;
import com.aegidea.photoshootingmanager.exception.PhotographerNotFoundException;
import com.aegidea.photoshootingmanager.exception.ScheduleOrderException;
import com.aegidea.photoshootingmanager.repository.OrderRepository;
import com.aegidea.photoshootingmanager.repository.PhotographerRepository;
import com.aegidea.photoshootingmanager.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.Month;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
    
    @Autowired
    private PhotographerRepository photographerRepository;
    
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
        this.order.setDateTime(null);
        Order created = this.orderService.createNewOrder(this.order);
        assertEquals(OrderStatus.UNSCHEDULED, created.getStatus());
    }
    
    @Test
    public void scheduleOrderTest() {
        LocalDateTime date =  LocalDateTime.of(2021, Month.MARCH, 4, 12, 0);
        this.order.setDateTime(null);
        Order unscheduled = this.orderService.createNewOrder(this.order);        
        this.orderService.scheduleOrder(unscheduled.getId(), date);
        Order fromDb = this.orderRepository.findById(unscheduled.getId()).orElse(null);
        assertEquals(date, fromDb.getDateTime());
        assertEquals(OrderStatus.PENDING, fromDb.getStatus());      
    }
    
    @Test(expected=ScheduleOrderException.class)
    public void scheduleOrderNotBusinessTimeTest() {
        LocalDateTime notBusinessTime =  LocalDateTime.of(2021, Month.MARCH, 4, 22, 0);
        this.order.setDateTime(null);
        Order unscheduled = this.orderService.createNewOrder(this.order);       
        this.orderService.scheduleOrder(unscheduled.getId(), notBusinessTime);           
    }
        
    @Test(expected=OrderCancelledException.class)
    public void scheduleOrderCancelledTest() {
        LocalDateTime businessTime =  LocalDateTime.of(2021, Month.MARCH, 4, 12, 0);
        Order cancelled = this.orderService.createNewOrder(this.order); 
        cancelled.setStatus(OrderStatus.CANCEL);
        this.orderRepository.save(cancelled);
        this.orderService.scheduleOrder(cancelled.getId(), businessTime);           
    }
    
    @Test
    public void assignOrderToPhotographerTest() {
        // creata an order
        Order order = this.orderService.createNewOrder(this.order);
        // create a photographer
        Photographer ph = new Photographer();
        ph.setName("Marge");
        this.photographerRepository.save(ph);        
        // assign order to photographer
        Order assignedOrder = this.orderService.assignOrderToPhotographer(order.getId(), ph.getId());
        assertEquals(OrderStatus.ASSIGNED, assignedOrder.getStatus());
        assertNotNull(assignedOrder.getAssignedTo());
        assertEquals(assignedOrder.getAssignedTo().getId(), ph.getId());       
    }
    
    @Test(expected=AssignOrderException.class)
    public void assignOrderToPhotographerEmptyTest() {
        this.orderService.assignOrderToPhotographer("", null); 
    }
    
    @Test(expected=PhotographerNotFoundException.class)
    public void assignOrderToPhotographerNoPhotographerTest() {
        // creata an order
        Order order = this.orderService.createNewOrder(this.order);     
        this.orderService.assignOrderToPhotographer(order.getId(), "666");
    
    }
    
    @Test
    public void uploadOrderTest() {
        
    }
    
    @Test
    public void verifyOrderTest() {
        
    }
    
    @Test
    public void cancelOrderTest() {
        Order created = this.orderService.createNewOrder(this.order);
        this.orderService.cancelOrder(created.getId());
        Order fromDb = this.orderRepository.findById(created.getId()).orElse(null);
        assertEquals(OrderStatus.CANCEL, fromDb.getStatus());
        
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
