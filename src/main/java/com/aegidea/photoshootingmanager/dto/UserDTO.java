package com.aegidea.photoshootingmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel
public class UserDTO {

    @NotNull
    @ApiModelProperty(notes="the name of the contact", example="Homer", required=true)
    private String firstName;
    
    @NotNull
    @ApiModelProperty(notes="The surname of the contact", example="Simpson", required=true)
    private String lastName;
    
    @Email
    @NotNull
    @ApiModelProperty(notes="the email of the contact", example="homer@gmail.com", required=true)
    private String email;
    
    @NotNull
    @ApiModelProperty(notes="the mobile of the contact", example="+39666", required=true)
    private String mobileNumber;

    public UserDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    
}
