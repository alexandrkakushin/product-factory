package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "INFOBASES")
@Data
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
