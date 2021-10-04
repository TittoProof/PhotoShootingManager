package com.aegidea.photoshootingmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class ScheduleOrderDTO {
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @NotNull
    private LocalDateTime dateTime;

    public ScheduleOrderDTO() {
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
