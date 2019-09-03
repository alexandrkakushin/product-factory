package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "PROPERTIES")
@Data
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
