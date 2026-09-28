package com.sun.web.restful_web_service.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

// @AllArgsConstructor 
// @Getter 
// @Setter 
@Entity(name = "user_details")
public class User {
    @Id 
    @GeneratedValue 
    private Integer id;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @JsonIgnore
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("user_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @JsonProperty("date_of_birth")
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Size(min=2, message="Name should have at least 2 characters")
    @JsonProperty("user_name")
    private String name;
    
    @Past(message="User Birth Date should be in the past")
    private LocalDate birthDate;

    public User() {

    }

    public User(Integer id, String name, LocalDate birthDate) {
        super();
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }
    
}
