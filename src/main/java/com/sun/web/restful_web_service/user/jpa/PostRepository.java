package com.sun.web.restful_web_service.user.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sun.web.restful_web_service.user.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
