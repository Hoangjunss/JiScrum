package com.baconbao.JiScrum.dto.attachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentCreateDTO {
    private String name;

    private MultipartFile file;

    private Integer issueId;

}
