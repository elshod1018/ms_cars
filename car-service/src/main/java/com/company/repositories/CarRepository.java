package com.company.repositories;

import com.company.domains.Car;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {
}