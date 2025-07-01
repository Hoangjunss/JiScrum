package com.baconbao.JiScrum.service.impl;


import com.baconbao.JiScrum.dto.member.MemberCreateDTO;
import com.baconbao.JiScrum.dto.member.MemberDTO;
import com.baconbao.JiScrum.dto.member.MemberUpdateDTO;
import com.baconbao.JiScrum.exception.ResourceNotFoundException;
import com.baconbao.JiScrum.mapper.MemberMapper;
import com.baconbao.JiScrum.model.Member;
import com.baconbao.JiScrum.model.Project;
import com.baconbao.JiScrum.repository.MemberRepository;
import com.baconbao.JiScrum.service.MemberService;
import com.baconbao.JiScrum.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the MemberService interface.
 * Handles business logic related to Member operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ProjectService projectService;

    /**
     * Create a new member using the provided DTO.
     * Validates input and persists the entity.
     *
     * @param dto the DTO containing data to create the member
     * @return the created member as a DTO
     */
    @Override
    public MemberDTO createMember(MemberCreateDTO dto) throws BadRequestException {
        log.info("Creating new member for projectId: {}", dto.getProjectId());

        if (dto.getProjectId() == null) {
            log.error("Project ID is required but was null");
            throw new BadRequestException("Project ID must not be null");
        }

        if (dto.getRole() == null || dto.getRole().isEmpty()) {
            log.error("Role is required but was null or empty");
            throw new BadRequestException("Role must not be null or empty");
        }

        if (dto.getJoinedAt() == null) {
            log.error("Joined date is required but was null");
            throw new BadRequestException("Joined date must not be null");
        }

        if (dto.getStatus() == null) {
            log.error("Status is required but was null");
            throw new BadRequestException("Status must not be null");
        }

        Project project = projectService.getProjectEntityById(dto.getProjectId());

        Member member = MemberMapper.toEntity(dto, project);
        Member saved = memberRepository.save(member);
        log.debug("Member created successfully with ID: {}", saved.getId());

        return MemberMapper.toDTO(saved);
    }

    /**
     * Update an existing member using the provided DTO.
     * Applies only the fields that are provided.
     *
     * @param id the ID of the member to update
     * @param dto the DTO containing the updated values
     * @return the updated member as a DTO
     */
    @Override
    public MemberDTO updateMember(Integer id, MemberUpdateDTO dto) {
        log.info("Updating member with ID: {}", id);

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Member with ID {} not found for update", id);
                    return new ResourceNotFoundException("Member not found with id: " + id);
                });

        if (dto.getRole() != null) {
            member.setRole(Member.Role.valueOf(dto.getRole()));
        }
        if (dto.getJoinedAt() != null) {
            member.setJoinedAt(dto.getJoinedAt());
        }
        if (dto.getLeftAt() != null) {
            member.setLeftAt(dto.getLeftAt());
        }
        if (dto.getStatus() != null) {
            member.setStatus(dto.getStatus());
        }

        Member updated = memberRepository.save(member);
        log.debug("Member with ID {} updated successfully", id);

        return MemberMapper.toDTO(updated);
    }

    /**
     * Retrieve a member by ID.
     *
     * @param id the ID of the member to retrieve
     * @return the member as a DTO
     */
    @Override
    public MemberDTO getMemberById(Integer id) {
        log.info("Retrieving member with ID: {}", id);

        Member member = getMemberEntityById(id);

        return MemberMapper.toDTO(member);
    }

    @Override
    public Member getMemberEntityById(Integer id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Member with ID {} not found", id);
                    return new ResourceNotFoundException("Member not found with id: " + id);
                });
    }

    /**
     * Delete a member by ID.
     *
     * @param id the ID of the member to delete
     */
    @Override
    public void deleteMember(Integer id) {
        log.info("Deleting member with ID: {}", id);

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Member with ID {} not found for deletion", id);
                    return new ResourceNotFoundException("Member not found with id: " + id);
                });

        memberRepository.delete(member);
        log.info("Member with ID {} deleted successfully", id);
    }
}
