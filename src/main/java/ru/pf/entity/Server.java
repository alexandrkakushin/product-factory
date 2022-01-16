package ru.pf.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Сущность "Сервер"
 * @author a.kakushin
 */
@Getter
@Setter
@Entity
@Table(name = "SERVERS")
public class Server implements PfEntity<Server> {

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

    @Override
    public Long getId() {
        return this.id;
    }
}
