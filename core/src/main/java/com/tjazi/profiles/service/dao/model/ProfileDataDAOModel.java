package com.tjazi.profiles.service.dao.model;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by Krzysztof Wasiak on 16/10/15.
 */

@Entity
@Table(name="ProfileData")
public class ProfileDataDAOModel {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "UserUUID", unique = true, nullable = false)
    private UUID userUuid;

    @Column(name = "UserUUID", unique = true, nullable = false)
    private String userName;

    @Column(name = "UserUUID", unique = true, nullable = false)
    private String email;

    @Column(name = "UserUUID", unique = false, nullable = false)
    private String name;

    @Column(name = "UserUUID", unique = false, nullable = false)
    private String surname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
