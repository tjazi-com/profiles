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

    @Column(name = "ProfileUUID", unique = true, nullable = false)
    private UUID profileUuid;

    @Column(name = "UserName", unique = true, nullable = false)
    private String userName;

    @Column(name = "UserEmail", unique = true, nullable = false)
    private String userEmail;

    @Column(name = "Name", unique = false, nullable = true)
    private String name;

    @Column(name = "Surname", unique = false, nullable = true)
    private String surname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getProfileUuid() {
        return profileUuid;
    }

    public void setProfileUuid(UUID userUuid) {
        this.profileUuid = userUuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
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
