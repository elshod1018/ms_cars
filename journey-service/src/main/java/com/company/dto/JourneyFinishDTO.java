package com.company.dto;

import jakarta.validation.constraints.NotNull;

public record JourneyFinishDTO(
        @NotNull Integer carId,
        @NotNull String commentDescription) {
}
