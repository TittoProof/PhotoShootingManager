package com.aegidea.photoshootingmanager.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * Photographer are different from user. 
 * probably we want to handle them in different ways (bank accounts or similar).
 * 
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@Entity
@Table(name = "photographers")
public class Photographer implements Serializable {

    private static final long serialVersionUID = -8714413396143614170L;
    
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)")
    @Id
    private String id;
    
    private String name;

    public Photographer() {
    }
    
    

}
