package com.company.service;

import com.company.domains.Car;
import com.company.dto.CarCreateDTO;
import com.company.dto.CarUpdateDTO;
import com.company.repositories.CarRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarRepository carRepository;

    public Car create(@NonNull CarCreateDTO dto) {
        return carRepository
                .save(Car.builder()
                        .name(dto.name())
                        .available(true)
                        .color(dto.color())
                        .model(dto.model())
                        .photo(dto.photo())
                        .build());
    }

    public Car getById(Integer id) {
        return carRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Car with id '%s' not found".formatted(id)));
    }

    public List<Car> getAll() {
        return carRepository.findAll();
    }

    public Car update(Integer id, CarUpdateDTO dto) {
        Car car = getById(id);
        car.setName(dto.name());
        car.setModel(dto.model());
        car.setColor(dto.color());
        car.setPhoto(dto.photo());
        return carRepository.save(car);
    }

    @Modifying
    @Transactional
    public void delete(Integer id) {
        carRepository.deleteById(id);
    }

    public boolean isAvailable(Integer id) {
        return getById(id).isAvailable();
    }

    public void bookCar(Integer id) {
        Car car = getById(id);
        car.setAvailable(false);
        carRepository.save(car);
    }

    public void discard(Integer id) {
        Car car = getById(id);
        car.setAvailable(true);
        carRepository.save(car);
    }
}
