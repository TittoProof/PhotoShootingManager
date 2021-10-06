package com.aegidea.photoshootingmanager.service;

import com.aegidea.photoshootingmanager.entity.Order;
import com.aegidea.photoshootingmanager.entity.Photographer;
import com.aegidea.photoshootingmanager.entity.User;
import com.aegidea.photoshootingmanager.enums.OrderStatus;
import com.aegidea.photoshootingmanager.exception.AssignOrderException;
import com.aegidea.photoshootingmanager.exception.OrderCancelledException;
import com.aegidea.photoshootingmanager.exception.OrderCannotVerifiedException;
import com.aegidea.photoshootingmanager.exception.OrderCreationException;
import com.aegidea.photoshootingmanager.exception.OrderNotFoundException;
import com.aegidea.photoshootingmanager.exception.ScheduleOrderException;
import com.aegidea.photoshootingmanager.repository.OrderRepository;
import io.micrometer.core.instrument.util.StringUtils;
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
    
    @Autowired
    private PhotographerService photographerService;
    
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
     * Set an order to PENDING and populate a valid dateTime.
     * 
     * @param orderId
     * @param date
     * @return 
     * @see R2
     * @since 1.0
     */
    public Order scheduleOrder(String orderId, LocalDateTime date) {        
        if(StringUtils.isEmpty(orderId) || date == null) {
            LOG.info("order and date cannot be null. Impossible to schedule order");
            throw new ScheduleOrderException("Cannot schedule an Order, invalid datas");
        } else if (!isDateTimeValid(date)) {
            LOG.info("date must be in business time. Impossible to schedule order");
            throw new ScheduleOrderException("Cannot schedule an Order, not in business time");
        } else {
            LOG.info("Scheduling order id#{} at {}", orderId, date.toString());
            Order fromDb = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
            checkCanceled(fromDb);
            fromDb.setDateTime(date);
            fromDb.setStatus(OrderStatus.PENDING);
            return this.orderRepository.save(fromDb);
        }
    }
    
    /**
     * 
     * @param orderId
     * @param photographerId
     * @return 
     * @see R3
     * @since 1.0
     */
    @Transactional
    public Order assignOrderToPhotographer(String orderId, String photographerId) {
        if (!StringUtils.isBlank(orderId) && !StringUtils.isBlank(photographerId)) {
            LOG.info("Assign order id#{} to photographer id#{}", orderId, photographerId);
            Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
            checkCanceled(order);
            Photographer photographer = this.photographerService.findById(photographerId);
            photographer.getOrders().add(order);
            order.setAssignedTo(photographer);
            order.setStatus(OrderStatus.ASSIGNED);
            return this.orderRepository.save(order);
           
        } else {
            LOG.info("OrderId and photographerId cannot be null. impossible to assign an order");
            throw new AssignOrderException("Impossible to assign order, no input!");
        }

        
    }
    
    /**
     * Very easy version, we ignore the upload of the Zip file.
     * here should be uploaded on S3 services maybe.
     * 
     * @param orderId
     * @see R4
     * @since 1.0
     * @return 
     */
    @Transactional
    public String uploadPhotosToOrder(String orderId) {
        if (!StringUtils.isBlank(orderId)) {
            LOG.info("Upload zip to order id#{} ", orderId);
            if (this.orderRepository.existsById(orderId)) {
                Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
                checkCanceled(order);
                this.orderRepository.updateOrderStatus(orderId, OrderStatus.UPLOADED);
                return order.getId();
            } else {
                throw new OrderNotFoundException("Order not found!");
            }
        } else {
            LOG.info("OrderId cannot be null. impossible to upload a zip to an order");
            throw new OrderNotFoundException("Impossible to upload, no input!");
        }
    }
    
    /**
     * Approve or reject an order.
     * 
     * @see R5
     * @since 1.0
     * @param orderId
     * @param isApproved
     * @return 
     */
    @Transactional
    public Order verifyOrder(String orderId, boolean isApproved) {
        if (!StringUtils.isBlank(orderId)) {
            LOG.info("Verify order id#{} ", orderId);
            if (this.orderRepository.existsById(orderId)) {
                Order order = this.orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found!"));
                checkCanceled(order);
                if (!order.getStatus().equals(OrderStatus.UPLOADED)) {
                    LOG.info("Impossible to verify order {}, only UPLOADED orders can be approved, but status#{}", orderId, order.getStatus());
                    throw new OrderCannotVerifiedException("Only PENDING orders can be approved or rejected");
                }
                if(isApproved) {
                    this.orderRepository.updateOrderStatus(orderId, OrderStatus.COMPLETED);
                    order.setStatus(OrderStatus.COMPLETED);
                } else {
                    this.orderRepository.updateOrderStatus(orderId, OrderStatus.ASSIGNED);
                    order.setStatus(OrderStatus.ASSIGNED);
                }
                return order;
            } else {
                throw new OrderNotFoundException("Order not found!");
            }
        } else {
            LOG.info("OrderId cannot be null. impossible to upload a zip to an order");
            throw new OrderNotFoundException("Impossible to upload, no input!");
        }
        
    }
    
    /**
     * Cancel (but not delete) an order.
     * 
     * @param orderId
     * @see R6
     * @since 1.0
     */
    @Transactional
    public void cancelOrder(String orderId) {
        LOG.info("Cancel order id#{}", orderId);
        if(this.orderRepository.existsById(orderId)) {
            this.orderRepository.updateOrderStatus(orderId, OrderStatus.CANCEL);
        } else {
            throw new OrderNotFoundException("Order not found!");
        }      
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
    
    /**
     * Support method for check if any operation are allowed.
     * No operation on cancelled order!
     * @param order 
     */
    private void checkCanceled(Order order) {
        if (order.getStatus() != null && order.getStatus().equals(OrderStatus.CANCEL)) {
            LOG.info("order id#{} is cancelled, no operation allowed", order.getId());
            throw new OrderCancelledException("Order is cancelled, any operation forbidden.");
        }
    }
    
    /**
     * This is a more performant way for update just the status of an order.
     * 
     * @see R0
     * @since 1.0
     * @param orderId
     * @param orderStatus 
     */
    @Transactional
    public int updateStatusOrder(String orderId, OrderStatus orderStatus) {
        LOG.info("updating status {} for order id#{}", orderStatus, orderId);
        return this.orderRepository.updateOrderStatus(orderId, orderStatus);
    }
    
}
