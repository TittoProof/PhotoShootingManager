package com.aegidea.photoshootingmanager.dto;

import com.aegidea.photoshootingmanager.enums.OrderStatus;
import com.aegidea.photoshootingmanager.enums.PhotoType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class OrderDTO {
    
    private String title;
    
    private String logisticInfo;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime dateTime;
    
    private PhotoType photoType;
    
    private OrderStatus status;
    
    private UserDTO contactData;

    public OrderDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogisticInfo() {
        return logisticInfo;
    }

    public void setLogisticInfo(String logisticInfo) {
        this.logisticInfo = logisticInfo;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

    public UserDTO getContactData() {
        return contactData;
    }

    public void setContactData(UserDTO contactData) {
        this.contactData = contactData;
    }
    
    

}
