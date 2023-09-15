package com.company.clients;

import com.company.dto.CarDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Service
@FeignClient(value = "${car-service.baseUrl}")
public interface CarClient {
    @GetMapping("/get/{id:.*}")
    CarDTO getCarById(@PathVariable Integer id);

    @GetMapping("/{id:.*}/is-available")
    boolean isAvailable(@PathVariable Integer id);

    @PutMapping("/{id:.*}/book")
    void bookCar(@PathVariable Integer id);

    @PutMapping("/{id:.*}/discard")
    void discard(@PathVariable Integer id);
}
