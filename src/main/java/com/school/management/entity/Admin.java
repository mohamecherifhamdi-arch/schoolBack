package com.school.management.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class Admin {
    @Id
    private String id;
    @Field("username")
    @NotBlank private String name;
    private String email;
    private String role;
    private String status;
    private String refreshToken;
    private String invitationToken;
    private String password;
    @Field("createdAt")
    private LocalDateTime createdAt;
}
