package com.capitalbanker.cbk.delivery.shared.model;


import javax.persistence.*;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name="code")
    private String code;

    @Column(name="description")
    private String description;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}