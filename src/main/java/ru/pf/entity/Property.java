package ru.pf.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "PROPERTIES")
public class Property implements PfEntity<Property, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

    private String comment;

    public Property() {
    }

    @Override
    public Long getId() {
        return this.id;
    }

    public String getName() {
    	return name;
    }
        
    public String getValue() {
        return value;
    }
    
    public String getComment() {
    	return comment;
    }
}
