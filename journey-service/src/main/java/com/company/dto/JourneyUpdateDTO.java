package com.company.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record JourneyUpdateDTO(
        @NotNull String description,
        @NotNull Integer carId,
        @NotNull LocalDate fromDay,
        @NotNull LocalDate toDay) {
}
