package com.baconbao.JiScrum.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberCreateDTO {

    private Integer projectId;

    private String role;

    private LocalDate joinedAt;

    private LocalDate leftAt;

    private Boolean status;
}
