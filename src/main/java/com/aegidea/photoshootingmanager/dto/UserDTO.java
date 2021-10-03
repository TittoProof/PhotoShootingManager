package com.aegidea.photoshootingmanager.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
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
    private String firstName;
    
    @NotNull
    private String lastName;
    
    @Email
    @NotNull
    private String email;
    
    @NotNull
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
