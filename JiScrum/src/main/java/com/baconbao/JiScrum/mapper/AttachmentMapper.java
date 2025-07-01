package com.baconbao.JiScrum.mapper;

import com.baconbao.JiScrum.dto.attachment.AttachmentCreateDTO;
import com.baconbao.JiScrum.dto.attachment.AttachmentDTO;
import com.baconbao.JiScrum.model.Attachment;

import java.time.LocalDateTime;

public class AttachmentMapper {
    public static Attachment toEntity(AttachmentCreateDTO attachmentCreateDTO){
        return Attachment.builder()
                .name(attachmentCreateDTO.getName())
                .uploadedAt(LocalDateTime.now())
                .build();
    }

    public static AttachmentDTO toDTO(Attachment attachment) {
        return AttachmentDTO.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .path(attachment.getPath())
                .type(attachment.getType())
                .size(attachment.getSize())
                .issueId(attachment.getIssue().getId())
                .uploadedById(attachment.getUploadedBy().getId())
                .build();
    }
}
