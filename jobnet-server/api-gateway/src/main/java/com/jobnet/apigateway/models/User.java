package com.jobnet.apigateway.models;

import com.jobnet.apigateway.models.enums.ERole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private ERole role;
    private Boolean locked;
    private Boolean enabled;
}