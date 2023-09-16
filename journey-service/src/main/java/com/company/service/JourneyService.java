package com.company.service;

import com.company.clients.*;
import com.company.domains.Journey;
import com.company.dto.CommentCreateDTO;
import com.company.dto.JourneyCreateDTO;
import com.company.dto.JourneyFinishDTO;
import com.company.dto.JourneyUpdateDTO;
import com.company.repositories.JourneyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JourneyService {
    private final JourneyRepository journeyRepository;
    private final CarClient carClient;
    private final CommentClient commentClient;

    public Journey create(JourneyCreateDTO dto) {
        Integer carId = dto.carId();
        boolean isAvailable = carClient.isAvailable(carId);
        if (!isAvailable) {
            throw new RuntimeException("Car with id '%s' is not available".formatted(carId));
        }
        Journey journey = journeyRepository.save(Journey.builder()
                .description(dto.description())
                .carId(carId)
                .fromDay(dto.fromDay())
                .toDay(dto.toDay())
                .finished(false)
                .userId(1)
                .build());
        carClient.bookCar(carId);
        return journey;
    }

    public List<Journey> getAll() {
        return journeyRepository.findAll();
    }

    public Journey getById(Integer id) {
        return journeyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Journey with id '%s' not found".formatted(id)));
    }

    public Journey update(Integer id, JourneyUpdateDTO dto) {
        Journey journey = getById(id);
        journey.setDescription(dto.description());
        journey.setCarId(dto.carId());
        journey.setFromDay(dto.fromDay());
        journey.setToDay(dto.toDay());
        journey.setFinished(false);
        journey.setUserId(1);
        return journeyRepository.save(journey);
    }

    public Journey finish(Integer id, JourneyFinishDTO dto) {
        Journey journey = getById(id);
        journey.setFinished(true);
        journeyRepository.save(journey);
        carClient.discard(journey.getCarId());
        commentClient.createComment(new CommentCreateDTO(dto.commentDescription(), journey.getCarId(), journey.getId()));
        return journey;
    }
}
