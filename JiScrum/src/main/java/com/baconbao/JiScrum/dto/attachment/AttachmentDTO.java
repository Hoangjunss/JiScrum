package com.baconbao.JiScrum.dto.attachment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentDTO {
    private Integer id;
    private String name;
    private String path;
    private Long size;
    private String type;

    private Integer issueId;
    private Integer uploadedById;

    private LocalDateTime uploadedAt;
}
