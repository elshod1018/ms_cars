package com.company.controller;

import com.company.domains.Car;
import com.company.dto.CarCreateDTO;
import com.company.dto.CarUpdateDTO;
import com.company.service.CarService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/cars")
public class CarController {
    private final CarService carService;

    @PostMapping("/create")
    public ResponseEntity<Car> create(@RequestBody CarCreateDTO dto) {
        Car car = carService.create(dto);
        return ResponseEntity.ok(car);
    }

    @GetMapping("get/all")
    public ResponseEntity<List<Car>> getAll() {
        List<Car> cars = carService.getAll();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/get/{idd}")
    public ResponseEntity<Car> get(@PathVariable(name = "idd") Integer id) {
        Car car = carService.getById(id);
        return ResponseEntity.ok(car);
    }

    @PutMapping("/update/{id:.*}")
    public ResponseEntity<Car> update(@PathVariable Integer id, @RequestBody CarUpdateDTO dto) {
        Car car = carService.update(id, dto);
        return ResponseEntity.ok(car);
    }

    @DeleteMapping("/delete/{id:.*}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        carService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{id:.*}/is-available")
    public ResponseEntity<Boolean> isAvailable(@PathVariable Integer id) {
        return ResponseEntity.ok(carService.isAvailable(id));
    }

    @PutMapping("/{id:.*}/book")
    public ResponseEntity<Boolean> bookCar(@PathVariable Integer id) {
        carService.bookCar(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping("/{id:.*}/discard")
    public ResponseEntity<Boolean> discard(@PathVariable Integer id) {
        carService.discard(id);
        return ResponseEntity.ok(true);
    }
}
