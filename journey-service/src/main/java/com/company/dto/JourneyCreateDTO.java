package com.company.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record JourneyCreateDTO(
        @NotNull String description,
        @NotNull Integer carId,
        @NotNull LocalDate fromDay,
        @NotNull LocalDate toDay) {
}
