package com.company.dto;

import jakarta.validation.constraints.NotNull;

public record CarCreateDTO(@NotNull
                           String name,
                           @NotNull
                           String model,
                           @NotNull
                           String color,
                           String photo) {
}
