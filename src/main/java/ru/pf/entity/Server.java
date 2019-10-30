package ru.pf.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Сущность "Сервер"
 * @author a.kakushin
 */
@Entity
@Table(name = "SERVERS")
@Data
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

}
