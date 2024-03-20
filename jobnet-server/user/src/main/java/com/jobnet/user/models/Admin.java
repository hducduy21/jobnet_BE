package com.jobnet.user.models;

import com.jobnet.user.models.enums.EGender;
import com.jobnet.user.models.enums.ERole;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document("users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin extends User {
    private EGender gender;
    private String phone;
    private LocalDate dateOfBirth;

    @Builder
    public Admin(
        String id,
        String email,
        String password,
        String name,
        ERole role,
        Boolean locked,
        Boolean enabled,
        EGender gender,
        String phone,
        LocalDate dateOfBirth
    ) {
        super(id, email, password, name, role, locked, enabled);
        this.gender = gender;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

}
