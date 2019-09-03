package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Сущность "Операционная система"
 * @author a.kakushin
 */
@Entity
@Table(name = "OS")
public class Os implements PfEntity<Os, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    public Os() {}

    @Override
    public Long getId() {
        return this.id;
    }
    
    public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}    
}
