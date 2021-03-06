package com.aegidea.photoshootingmanager.dto;

import com.aegidea.photoshootingmanager.enums.PhotoType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class CreateOrderDTO {
    
    private UserDTO contactData;
    
    @ApiModelProperty(notes="Title of the order", example="Portraits", required=false)
    private String title;
    
    @ApiModelProperty(notes="Some logistic infos", example="nikon 5000", required=false)
    private String logisticInfo;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @ApiModelProperty(notes="the Date of the order", example="2021-03-04T12:00:00Z", required=false)
    private LocalDateTime dateTime;
    
    private PhotoType photoType;

    public CreateOrderDTO() {
    }

    public UserDTO getContactData() {
        return contactData;
    }

    public void setContactData(UserDTO contactData) {
        this.contactData = contactData;
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
    

}
