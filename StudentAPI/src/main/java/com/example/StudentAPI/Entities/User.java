package com.example.StudentAPI.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "is_super_use")
    private Boolean isSuperUse;

    public User() {
    }

    public User(Integer id, String username, String password, Boolean isSuperUse) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isSuperUse = isSuperUse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSuperUse() {
        return isSuperUse;
    }

    public void setSuperUse(Boolean superUse) {
        isSuperUse = superUse;
    }
}
