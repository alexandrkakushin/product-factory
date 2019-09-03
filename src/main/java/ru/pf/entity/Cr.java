package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CR - Configuration Repository
 * @author a.kakushin
 */
@Entity
@Table(name = "CR")
public class Cr implements PfEntity<Cr, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    private String address;

    private String login;
    private String password;

    public Cr() {}

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

	public String getAddress() {
        return address;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
