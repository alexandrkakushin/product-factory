package ru.pf.entity;

import lombok.Data;

import javax.persistence.*;

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

    public Server() {}

    @Override
    public Long getId() {
        return this.id;
    }

    public Os getOs() {
        return os;
    }

    public void setOs(Os os) {
        this.os = os;
    }
}
