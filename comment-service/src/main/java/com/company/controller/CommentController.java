package com.company.controller;

import com.company.domains.Comment;
import com.company.dto.CommentCreateDTO;
import com.company.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<Comment> create(@RequestBody CommentCreateDTO dto) {
        Comment comment = commentService.create(dto);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Comment>> getAll() {
        List<Comment> comments = commentService.getAll();
        return ResponseEntity.ok(comments);
    }
    @GetMapping("/get/by-car/{carId:.*}")
    public ResponseEntity<List<Comment>> getByCarId(@PathVariable Integer carId) {
        List<Comment> comments = commentService.getByCarId(carId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/get/{id:.*}")
    public ResponseEntity<Comment> getById(@PathVariable Integer id) {
        Comment comment = commentService.getById(id);
        return ResponseEntity.ok(comment);
    }
}
