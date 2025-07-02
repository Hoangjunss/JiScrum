package com.baconbao.JiScrum.mapper;

import com.baconbao.JiScrum.dto.member.MemberCreateDTO;
import com.baconbao.JiScrum.dto.member.MemberDTO;
import com.baconbao.JiScrum.model.Member;
import com.baconbao.JiScrum.model.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utility class for converting between Member entity and DTOs.
 */
public class MemberMapper {

    /**
     * Convert a MemberCreateDTO to a Member entity.
     *
     * @param dto the DTO containing data to create the member
     * @param project the associated Project entity (fetched externally)
     * @return a new Member entity
     */
    public static Member toEntity(MemberCreateDTO dto, Project project) {
        // Build a new Member entity using builder pattern
        return Member.builder()
                .project(project)
                .role(Member.Role.valueOf(dto.getRole()))
                .status(dto.getStatus())
                .joinedAt(LocalDateTime.now())
                .build();
    }

    /**
     * Convert a Member entity to MemberResponseDTO.
     *
     * @param member the Member entity
     * @return the DTO representation of the member
     */
    public static MemberDTO toDTO(Member member) {
        // Convert and extract only needed fields for response
        return MemberDTO.builder()
                .id(member.getId())
                .projectId(member.getProject().getId())
                .role(String.valueOf(member.getRole()))
                .joinedAt(member.getJoinedAt())
                .leftAt(member.getLeftAt())
                .status(member.getStatus())
                .build();
    }
}

