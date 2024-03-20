package com.jobnet.location.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Province {

    private String id;
    private String name;
    private String slug;
    private String type;
    private String nameWithType;
    private String code;
}

