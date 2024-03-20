package com.jobnet.clients.user.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RawRecruiterResponse {
    private String id;
    private String email;
    private String name;
    private String role;
    private String phone;
    private String profileImageId;
    private String nation;
    private boolean activeBusiness;
    private String businessId;
    private Boolean locked;
}
