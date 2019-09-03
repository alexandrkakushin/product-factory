package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Сущность "Сервер"
 * @author a.kakushin
 */
@Entity
@Table(name = "SERVERS")
public class Server implements PfEntity<Server, Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "os_id")
    private Os os;

    private Integer cpu;
    private Integer ram;
    private String ip4;

    public Server() {}

    @Override
    public Long getId() {
        return this.id;
    }

    public String getName() {
    	return name;
    }
    
    public Os getOs() {
        return os;
    }

    public void setOs(Os os) {
        this.os = os;
    }
    
	public Integer getCpu() {
		return cpu;
	}

	public Integer getRam() {
		return ram;
	}

	public String getIp4() {
		return ip4;
	}

	public String getComment() {
		return comment;
	}
}
