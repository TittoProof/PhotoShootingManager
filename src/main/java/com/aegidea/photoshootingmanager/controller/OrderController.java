package com.aegidea.photoshootingmanager.controller;

import com.aegidea.photoshootingmanager.dto.CreateOrderDTO;
import com.aegidea.photoshootingmanager.dto.OrderDTO;
import com.aegidea.photoshootingmanager.dto.PhotographerDTO;
import com.aegidea.photoshootingmanager.dto.ScheduleOrderDTO;
import com.aegidea.photoshootingmanager.entity.Order;
import com.aegidea.photoshootingmanager.entity.Photographer;
import com.aegidea.photoshootingmanager.service.OrderService;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@RestController
@RequestMapping("/manager")
@Api(tags = "Admin operations")
public class OrderController {

    private final static Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderService orderService;

    @Timed
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/orders", method = RequestMethod.POST)
    @ApiOperation(value = "Create a new order")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid request or constraints not respected")
        ,
        @ApiResponse(code = 201, message = "created")
    })
    public OrderDTO createNewOrder(@Valid @RequestBody CreateOrderDTO createOrderDto) {
        LOG.info("Start creating a new order.");
        Order toBeCreated = this.modelMapper.map(createOrderDto, Order.class);
        Order result = this.orderService.createNewOrder(toBeCreated);
        return this.modelMapper.map(result, OrderDTO.class);
    }
    
    
    @Timed
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/orders/{orderId}", method = RequestMethod.POST)
    @ApiOperation(value = "Schedule order")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid request or constraints not respected")
        ,
        @ApiResponse(code = 200, message = "updated")
    })
    public void scheduleOrder(@PathVariable("orderId") String orderId, @Valid @RequestBody ScheduleOrderDTO scheduleOrderDto) {
        LOG.info("Start scheduling order {}.", orderId);
        this.orderService.scheduleOrder(orderId, scheduleOrderDto.getDateTime());
        
    }
    
    @Timed
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/orders/{orderId}/cancel", method = RequestMethod.POST)
    @ApiOperation(value = "Cancel an order")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid request or constraints not respected")
        ,
        @ApiResponse(code = 200, message = "cancelled")
    })
    public void cancelOrder(@PathVariable("orderId") String orderId) {
        LOG.info("Start cancelling order {}.", orderId);
        this.orderService.cancelOrder(orderId);
        
    }
    
    @Timed
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/orders/{orderId}/photographers/{photographerId}", method = RequestMethod.POST)
    @ApiOperation(value = "assign an order to a specific photographer")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid request or constraints not respected")
        ,
        @ApiResponse(code = 200, message = "assigned")
    })
    public OrderDTO assignOrder(@PathVariable("orderId") String orderId, @PathVariable("photographerId") String photographerId) {
        LOG.info("Start assignig order {} to photographer.", orderId, photographerId);
        Order result = this.orderService.assignOrderToPhotographer(orderId, photographerId);
        return this.modelMapper.map(result, OrderDTO.class);
        
    }

}
