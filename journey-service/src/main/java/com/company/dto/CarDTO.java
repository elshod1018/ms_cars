package com.company.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarDTO {
    private Integer id;

    private String name;

    private String model;

    private String color;

    private String photo;

    private boolean available;
}
