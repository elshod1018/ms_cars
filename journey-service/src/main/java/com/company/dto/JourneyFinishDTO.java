package com.company.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record JourneyFinishDTO(
        @NotNull Integer carId,
        @NotNull String comment) {
}
