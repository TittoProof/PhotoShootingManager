package com.aegidea.photoshootingmanager.service;

import com.aegidea.photoshootingmanager.entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@Service
public class OrderService {
    
    private final static Logger LOG = LoggerFactory.getLogger(OrderService.class);
    
    
    /**
     * Create a new Order.
     * Some fields are mandatory. 
     * You can create orders without a dateTime (unscheduled order).
     * 
     * @see R1
     * @since 1.0
     * @return 
     */
    public Order createNewOrder() {
        throw new UnsupportedOperationException();
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

}
