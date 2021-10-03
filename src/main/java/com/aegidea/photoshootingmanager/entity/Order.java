package com.aegidea.photoshootingmanager.entity;

import com.aegidea.photoshootingmanager.enums.OrderStatus;
import com.aegidea.photoshootingmanager.enums.PhotoType;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Tiziano (Titto) Fortin <tiz.fortin@gmail.com>
 */
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    
    private static final long serialVersionUID = -7142280447281545585L;
    
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)")
    @Id
    private String id;
    
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL,  orphanRemoval = false)
    private User contactData;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "photographer_id")
    private Photographer assignedTo;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private PhotoType photoType;
    
    private String title;
    
    private String logisticInfo;
    
    private LocalDateTime dateTime;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getContactData() {
        return contactData;
    }

    public void setContactData(User contactData) {
        this.contactData = contactData;
    }

    public Photographer getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Photographer assignedTo) {
        this.assignedTo = assignedTo;
    }

    public PhotoType getPhotoType() {
        return photoType;
    }

    public void setPhotoType(PhotoType photoType) {
        this.photoType = photoType;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    

}
