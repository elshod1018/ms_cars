package com.company.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSMS implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, updatable = false)
    private String code;

    @Column(nullable = false, updatable = false)
    private Integer userId;

    @Builder.Default
    @CreationTimestamp
    private LocalDateTime fromTime = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime toTime = LocalDateTime.now().plusMinutes(2);

    private boolean expired;
}
