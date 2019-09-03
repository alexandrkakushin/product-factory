package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Сущность "Служба сервера"
 * @author a.kakushin
 */
@Entity
@Table(name = "SERVICES")
public class Service implements PfEntity<Service, Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    public Service() {}

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
