package com.baconbao.JiScrum.service;

import com.baconbao.JiScrum.dto.attachment.AttachmentCreateDTO;
import com.baconbao.JiScrum.dto.attachment.AttachmentDTO;
import com.baconbao.JiScrum.dto.attachment.AttachmentUpdateDTO;
import com.baconbao.JiScrum.model.Attachment;

public interface AttachmentService {
    AttachmentDTO createAttachment(AttachmentCreateDTO attachmentCreateDTO);
    AttachmentDTO updateAttachment(AttachmentUpdateDTO attachmentUpdateDTO, Integer id);
    AttachmentDTO getAttachmentById(Integer id);
    Attachment getAttachmentEntityById(Integer id);
    void deleteAttachment(Integer id);
}
