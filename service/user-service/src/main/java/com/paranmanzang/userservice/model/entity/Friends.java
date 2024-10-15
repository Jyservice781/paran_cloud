package com.paranmanzang.userservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="friends")
public class Friends {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String responseUser;

    @Column(nullable = false)
    private String requestUser;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime request_at;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime response_at;
    //
    @PrePersist
    public void prePersist() {
        if (request_at == null) {
            request_at = LocalDateTime.now();
        }
        if (response_at == null) {
            response_at = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        response_at = LocalDateTime.now();
    }

}
