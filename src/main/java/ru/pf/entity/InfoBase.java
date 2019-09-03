package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "INFOBASES")
public class InfoBase implements PfEntity<InfoBase, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

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
