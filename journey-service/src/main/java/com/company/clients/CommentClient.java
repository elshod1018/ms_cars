package com.company.clients;

import com.company.dto.CarDTO;
import com.company.dto.CommentCreateDTO;
import com.company.dto.CommentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(value = "${comment-service.baseUrl}")
public interface CommentClient {
    @GetMapping("/get/by-car/{carId:.*}")
    CommentDTO getCommentByCarId(@PathVariable Integer carId);

    @PostMapping("/create")
    CommentDTO createComment(@RequestBody CommentCreateDTO dto);
}
