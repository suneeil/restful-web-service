package com.sun.web.restful_web_service.user;

import java.net.URI;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController 
public class UserResource {

    
    private UserDaoService service;
    private Integer savedUserId;

    public UserResource(UserDaoService service) {
        this.service = service;
    }
    
    //GET /users
    @GetMapping("/users")
    public ResponseEntity<List<User>> retrieveAllUsers() {
        return ResponseEntity.ok(service.findAll());
        //return service.findAll();
    }

    //GET /users/id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        User user = service.findOneUser(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        User savedUser = service.saveUser(user);         
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * This method creates a new user and returns the location of the newly created user in the response header.
     * The location is constructed using the current request URI and the ID of the newly created user.
     * The Created status code (201) is returned along with the user object in the response body.
     * @param user
     * @return
     */
     @PostMapping("/create-users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.saveUser(user);
        savedUserId = savedUser.getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUserId)
                .toUri();
         return ResponseEntity.created(location).body(savedUser);
        
    }
    
    @DeleteMapping ("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable int id) {
        User deletedUser = service.deleteUser(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return ResponseEntity.ok(deletedUser);  
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> putMethodName(@PathVariable int id, @RequestBody User user) {
        User userData = service.updateUser(id, user)
                .orElseThrow(() -> new UserNotFoundException(id));
        return ResponseEntity.ok(userData);
    }

}
