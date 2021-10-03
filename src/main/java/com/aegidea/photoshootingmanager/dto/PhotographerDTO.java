package com.aegidea.photoshootingmanager.dto;

import com.aegidea.photoshootingmanager.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class PhotographerDTO {
    
    @ApiModelProperty(example = "Photographer1", required = true)
    private String name;
    
    @ApiModelProperty(required = false)
    private List<Order> orders;

    public PhotographerDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    

}
