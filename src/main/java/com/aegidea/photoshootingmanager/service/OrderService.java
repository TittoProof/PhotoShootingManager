package com.aegidea.photoshootingmanager.service;

import com.aegidea.photoshootingmanager.dto.CreateOrderDTO;
import com.aegidea.photoshootingmanager.entity.Order;
import com.aegidea.photoshootingmanager.entity.User;
import com.aegidea.photoshootingmanager.enums.OrderStatus;
import com.aegidea.photoshootingmanager.exception.OrderCreationException;
import com.aegidea.photoshootingmanager.repository.OrderRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@Service
public class OrderService {
    
    private final static Logger LOG = LoggerFactory.getLogger(OrderService.class);    
    
    @Autowired
    private OrderRepository orderRepository;
    
    /**
     * Create a new Order.
     * Some fields are mandatory. 
     * You can create orders without a dateTime (unscheduled order).
     * 
     * @param newOrder
     * @see R1
     * @since 1.0
     * @return 
     */
    @Transactional
    public Order createNewOrder(Order newOrder) {
        LOG.info("New Order creation starts.");
        // check for mandatory datas.
        if (!isDateTimeValid(newOrder.getDateTime())) {
            LOG.info("Date is not in Business Range");
            throw new OrderCreationException("Cannot create a new Order, Date is not in valid business time range");
        }        
        if(!isContactDateValid(newOrder.getContactData())) {
            LOG.info("No valid ContactData sent");
            throw new OrderCreationException("Cannot create a new Order, the contact data is not valid");
        }
        if (newOrder.getPhotoType() == null) {
            LOG.info("No photoType provided");
            throw new OrderCreationException("Cannot create a new Order, the photoType should not be null");
        }
        // Assign the correct status
        if(newOrder.getDateTime() != null) {            
            newOrder.setStatus(OrderStatus.PENDING);
        } else {
            newOrder.setStatus(OrderStatus.UNSCHEDULED);
        }
        Order result = this.orderRepository.save(newOrder);
        LOG.info("Order id#{} successful created", result.getId());
        return result;
    }
    
    /**
     * Set an order to PENDING and populate a current dateTime.
     * 
     * @see R2
     * @since 1.0
     */
    public void scheduleOrder() {
        throw new UnsupportedOperationException();
    }
    
    public void assignOrderToPhotographer() {
        throw new UnsupportedOperationException();
    }
    
    public Order uploadPhotosToOrder() {
        throw new UnsupportedOperationException();
    }
    
    public Order verifyOrder() {
        throw new UnsupportedOperationException();
    }
    
    public void cancelOrder() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * Check if the contact Datas are correctly sent.
     * 
     * @see R1
     * @since 1.0
     * @param contactData
     * @return 
     */
    public boolean isContactDateValid(User contactData) {
        if (contactData == null)
            return false;
        if (contactData.getEmail() == null)
            return false;
        if (contactData.getFirstName() == null)
            return false;
        if (contactData.getLastName() == null)
            return false;
        if (contactData.getMobileNumber() == null)
            return false;
        return true;
    }
    
    /**
     * Check if a date time is in business time (8:00-20:00).
     * 
     * @param dateTime
     * @see R1
     * @return 
     */
    public boolean isDateTimeValid(LocalDateTime dateTime) {
        if (dateTime != null) {
            return !(dateTime.toLocalTime().isBefore(LocalTime.of(8, 0)) || dateTime.toLocalTime().isAfter(LocalTime.of(20, 0)));
        }
        return true; // default if date is null
    }

}
