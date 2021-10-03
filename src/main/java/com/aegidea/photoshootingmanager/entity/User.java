package com.aegidea.photoshootingmanager.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * Those are the users that request for a photoshoot.
 * 
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = -2372735083385397677L; 
    
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)")
    @Id
    private String id;
    
    @NotNull
    private String firstName;
    
    @NotNull
    private String lastName;
    
    @Email
    @NotNull
    private String email;
    
    @NotNull
    private String mobileNumber;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_contactData")
    private Order order;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    
}
