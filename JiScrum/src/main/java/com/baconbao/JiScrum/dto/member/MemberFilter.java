package com.baconbao.JiScrum.dto.member;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberFilter {
    private Integer projectId;
    private String role;
    private LocalDateTime startJoinDate;
    private LocalDateTime endJoinDate;
    private Boolean status;
}
