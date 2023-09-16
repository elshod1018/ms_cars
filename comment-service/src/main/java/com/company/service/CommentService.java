package com.company.service;

import com.company.domains.Comment;
import com.company.dto.CommentCreateDTO;
import com.company.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(CommentCreateDTO dto) {
        return commentRepository.save(Comment.builder()
                .description(dto.description())
                .carId(dto.carId())
                .journeyId(dto.journeyId())
                .userId(1)
                .build());
    }

    public Comment getById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Comment with id '%s' not found".formatted(id)));
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public List<Comment> getByCarId(Integer carId) {
        return commentRepository.findAllByCarId(carId);
    }
}
