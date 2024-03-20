package com.jobnet.user.models;

import com.jobnet.user.models.enums.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@NoArgsConstructor
@Getter
@Setter
public class Recruiter extends User{
    private String phone;
    private String profileImageId;
    private boolean activeBusiness;
    private String businessId;
    private String nation;

    @Builder
    public Recruiter(
            String id,
            String email,
            String password,
            String name,
            ERole role,
            Boolean locked,
            Boolean enabled,
            String phone,
            String profileImageId,
            String businessId,
            String nation,
            boolean activeBusiness
    ) {
        super(id, email, password, name, role, locked, enabled);
        this.phone = phone;
        this.profileImageId = profileImageId;
        this.businessId = businessId;
        this.nation = nation;
        this.activeBusiness = activeBusiness;
    }

}
