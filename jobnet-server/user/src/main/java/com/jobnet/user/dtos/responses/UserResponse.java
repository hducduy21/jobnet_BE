package com.jobnet.user.dtos.responses;

import com.jobnet.user.models.enums.ERole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(of = {"id", "name"})
public class UserResponse {
    private String id;
    private String email;
    private String name;
    private ERole role;
}
