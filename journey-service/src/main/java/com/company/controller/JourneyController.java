package com.company.controller;

import com.company.domains.Journey;
import com.company.dto.JourneyCreateDTO;
import com.company.dto.JourneyFinishDTO;
import com.company.dto.JourneyUpdateDTO;
import com.company.service.JourneyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/journeys")
public class JourneyController {
    private final JourneyService journeyService;

    @PostMapping("/create")
    public ResponseEntity<Journey> create(@RequestBody JourneyCreateDTO dto) {
        Journey journey = journeyService.create(dto);
        return ResponseEntity.ok(journey);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<Journey>> getAll() {
        List<Journey> journeys = journeyService.getAll();
        return ResponseEntity.ok(journeys);
    }

    @GetMapping("/get/{id:.*}")
    public ResponseEntity<Journey> getById(@PathVariable Integer id) {
        Journey journey = journeyService.getById(id);
        return ResponseEntity.ok(journey);
    }

    @PutMapping("/update/{id:.*}")
    public ResponseEntity<Journey> update(@PathVariable Integer id, @RequestBody JourneyUpdateDTO dto) {
        Journey journey = journeyService.update(id, dto);
        return ResponseEntity.ok(journey);
    }

    @PutMapping("/finish/{id:.*}")
    public ResponseEntity<Journey> finish(@PathVariable Integer id, @RequestBody JourneyFinishDTO dto) {
        Journey journey = journeyService.finish(id, dto);
        return ResponseEntity.ok(journey);
    }
}
