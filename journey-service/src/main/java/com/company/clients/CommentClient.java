package com.company.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(value = "${comment-service.baseUrl}")
public interface CommentClient {

}
