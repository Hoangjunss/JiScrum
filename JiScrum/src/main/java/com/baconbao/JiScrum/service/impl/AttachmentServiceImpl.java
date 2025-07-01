package com.baconbao.JiScrum.service.impl;

import com.baconbao.JiScrum.dto.attachment.AttachmentCreateDTO;
import com.baconbao.JiScrum.dto.attachment.AttachmentDTO;
import com.baconbao.JiScrum.dto.attachment.AttachmentUpdateDTO;
import com.baconbao.JiScrum.exception.ResourceNotFoundException;
import com.baconbao.JiScrum.mapper.AttachmentMapper;
import com.baconbao.JiScrum.model.Attachment;
import com.baconbao.JiScrum.model.Issue;
import com.baconbao.JiScrum.repository.AttachmentRepository;
import com.baconbao.JiScrum.service.AttachmentService;
import com.baconbao.JiScrum.service.IssueService;
import com.baconbao.JiScrum.service.MemberService;
import com.baconbao.JiScrum.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;

    private final IssueService issueService;
    private final MemberService memberService;


    @Override
    public AttachmentDTO createAttachment(AttachmentCreateDTO attachmentCreateDTO) {
        Issue issue = issueService.getIssueEntityById(attachmentCreateDTO.getIssueId());

        Attachment attachment = AttachmentMapper.toEntity(attachmentCreateDTO);
        attachment.setId(IdGenerator.getGenerationId());
        attachment.setIssue(issue);

        if(attachmentCreateDTO.getFile() != null) {
            // TODO: Process file is here
            //attachment.setPath();
            //attachment.setType();
            //attachment.setSize();
        }

        return AttachmentMapper.toDTO(attachmentRepository.save(attachment));
    }

    @Override
    public AttachmentDTO updateAttachment(AttachmentUpdateDTO attachmentUpdateDTO, Integer id) {
        Attachment attachment = getAttachmentEntityById(id);

        if (attachmentUpdateDTO.getName() != null) {
            attachment.setName(attachmentUpdateDTO.getName());
        }

        if (attachmentUpdateDTO.getIssueId() != null) {
            if (attachmentUpdateDTO.getIssueId() == 0){
                attachment.setIssue(null);
            } else {
                Issue newIssue = issueService.getIssueEntityById(attachmentUpdateDTO.getIssueId());
                attachment.setIssue(newIssue);
            }
        }

        // ---- Xử lý file mới (nếu client upload) ------------------------
        if (attachmentUpdateDTO.getFile() != null && !attachmentUpdateDTO.getFile().isEmpty()) {

            /*attachment.setSize(file.getSize());
            attachment.setType(file.getContentType());
            attachment.setName(
                    dto.getName() != null ? dto.getName() : file.getOriginalFilename());*/
        }

        Attachment saved = attachmentRepository.save(attachment);
        return AttachmentMapper.toDTO(saved);
    }

    @Override
    public AttachmentDTO getAttachmentById(Integer id) {
        return AttachmentMapper.toDTO(getAttachmentEntityById(id));
    }

    @Override
    public Attachment getAttachmentEntityById(Integer id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Attachment with ID {} not found", id);
                    return new ResourceNotFoundException("Attachment not found with id: " + id);
                });
    }

    @Override
    public void deleteAttachment(Integer id) {
        Attachment attachment = getAttachmentEntityById(id);

        attachmentRepository.delete(attachment);
    }
}
