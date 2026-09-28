package com.sun.web.restful_web_service.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component 
public class UserDaoService {
//Save all data in JPA/Hibernate  > Database

    private static List<User> users = new ArrayList<>();
    private static int usersCount = 0;

    static {        
        users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Eve", LocalDate.now().minusYears(25)));
        users.add(new User(++usersCount, "Jack", LocalDate.now().minusYears(20)));
    }

    //public List<User> findAll() {
    public List<User> findAll() {
        return users;
    }

    //public User save(User user) {

    //public User findOne(int id) {
    public Optional<User> findOneUser(int id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
        // for(User user: users) {
        //     if(user.getId() == id) {
        //         return Optional.of(user);
        //     }
        // }
        // return Optional.empty();
    }

    public User saveUser(User user) {
        user.setId(++usersCount);
        users.add(user);
        return user;
    }

    public Optional<User> deleteUser(int id) {
        Optional<User> userOptional = findOneUser(id);
        if(userOptional.isPresent()) {
            users.remove(userOptional.get());
            return Optional.of(userOptional.get());
        }
        return Optional.empty();
    }

    public Optional<User> updateUser(int id, User user) {
        Optional<User> userOptional = findOneUser(id);
        if(userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setName(user.getName());
            existingUser.setBirthDate(user.getBirthDate());            
            return Optional.of(existingUser);
        }
        return Optional.empty();
    }

}
