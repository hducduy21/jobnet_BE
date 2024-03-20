package com.jobnet.user.dtos.responses;

import com.jobnet.clients.business.BusinessResponse;
import com.jobnet.user.models.enums.ERole;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString(of = {"id", "name"})
public class RecruiterResponse {

    private String id;
    private String email;
    private String name;
    private ERole role;
    private String phone;
    private String profileImageId;
    private String nation;
    private boolean activeBusiness;
    private BusinessResponse business;
    private Boolean locked;

}
