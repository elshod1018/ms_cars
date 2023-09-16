package com.company.repositories;

import com.company.domains.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select c from Comment c where c.carId = ?1")
    List<Comment> findAllByCarId(Integer carId);
}