package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author a.kakushin
 */
@Entity
@Table(name = "GIT")
@Data
public class Git implements PfEntity<Git, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    private String fetchUrl;
    private String defaultBranch;

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
        
    public String getFetchUrl() {
        return this.fetchUrl;
    }

    public String getDefaultBranch() {
        return this.defaultBranch;
    }
}
