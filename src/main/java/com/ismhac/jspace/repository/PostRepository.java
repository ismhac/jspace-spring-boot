package com.ismhac.jspace.repository;

import com.ismhac.jspace.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

}
