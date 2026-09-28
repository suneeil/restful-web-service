package com.sun.web.restful_web_service.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;

@Entity 
public class Post {

    @Id 
    @GeneratedValue 
    private int id;

    @Size(min = 10)
    private String description;
    


    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY) //Does not fetch the User details
    private User user;

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post [id=" + id + ", description=" + description + "]";
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

}
