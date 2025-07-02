package com.baconbao.JiScrum.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO {
    private Integer id;
    private Integer projectId;
    private String role;
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;
    private Boolean status;
}
