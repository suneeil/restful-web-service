package com.sun.web.restful_web_service.user.jpa;

import com.sun.web.restful_web_service.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

}
