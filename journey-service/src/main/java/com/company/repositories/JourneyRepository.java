package com.company.repositories;

import com.company.domains.Journey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JourneyRepository extends JpaRepository<Journey, Integer> {
}