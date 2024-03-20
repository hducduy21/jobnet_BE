package com.jobnet.clients.user.dtos.responses;

import com.jobnet.clients.business.BusinessResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RecruiterResponse {

    private String id;
    private String email;
    private String name;
    private String role;
    private String phone;
    private String profileImageId;
    private String nation;
    private boolean activeBusiness;
    private BusinessResponse business;
    private Boolean locked;
}
