package com.jobnet.resume.dtos.requests;

import com.jobnet.resume.validations.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileSaveRequest {

    @File
    private MultipartFile file;
}
