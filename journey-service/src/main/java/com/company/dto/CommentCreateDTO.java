package com.company.dto;

import jakarta.validation.constraints.NotNull;

public record CommentCreateDTO(@NotNull String description,
                               @NotNull Integer carId,
                               @NotNull Integer journeyId) {
}
