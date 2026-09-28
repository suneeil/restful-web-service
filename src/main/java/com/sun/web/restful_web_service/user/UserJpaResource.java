package com.sun.web.restful_web_service.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sun.web.restful_web_service.user.jpa.PostRepository;
import com.sun.web.restful_web_service.user.jpa.UserRepository;

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
public class UserJpaResource {

    

    private Integer savedUserId;
    private UserRepository userRepository;
    private PostRepository postRepository;
    private Optional<User> byId;

    public UserJpaResource(UserRepository repository, PostRepository postRepository) {        
        this.userRepository = repository;
        this.postRepository = postRepository;
    }
    
    //GET /users
    @GetMapping("/jpa/users")
    public ResponseEntity<List<User>> retrieveAllUsers() { //ResponseEntity<List<User>> 
        return ResponseEntity.ok(userRepository.findAll());
        //return service.findAll();
    }

    //GET /users/id
    @GetMapping("/jpa/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {

        return ResponseEntity.ok().body(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id)));
        
    }

    @GetMapping("/jpa/users/{id}/posts")
    public List<Post> getPostsForUser(@PathVariable int id) {
        Optional<User> foundUser = userRepository.findById(id);
        if(foundUser.isEmpty()) {
            throw new UserNotFoundException(id);
        } 
        return foundUser.get().getPosts();         
    }
    

    @PostMapping("/jpa/users")
    public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);         
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * This method creates a new user and returns the location of the newly created user in the response header.
     * The location is constructed using the current request URI and the ID of the newly created user.
     * The Created status code (201) is returned along with the user object in the response body.
     * @param user
     * @return
     */
     @PostMapping("/jpa/create-users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepository.save(user);
        savedUserId = savedUser.getId();
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUserId)
                .toUri();
         return ResponseEntity.created(location).body(savedUser);
        
    }

    @PostMapping("/jpa/users/{userId}/posts")
    public ResponseEntity<Post> postMethodName(@Valid @RequestBody Post post, @PathVariable int userId) {
        
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        post.setUser(user.get());
        Post savedPost = postRepository.save(post);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
         return ResponseEntity.created(location).body(savedPost);
    }
    
    
    @DeleteMapping ("/jpa/users/{id}")
    public void deleteUser(@PathVariable int id) {
        
        if(userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);    
        } 
    }

    @PutMapping("/jpa/users/{id}")
    public ResponseEntity<User> putMethodName(@PathVariable int id, @RequestBody User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
                existingUser.setBirthDate(user.getBirthDate());
                existingUser.setName(user.getName());
                User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }

}
