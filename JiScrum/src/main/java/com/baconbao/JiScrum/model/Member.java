package com.baconbao.JiScrum.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "member_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_project_id", nullable = false)
    @JsonBackReference
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "member_role")
    private Role role;

    @Column(name = "member_joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "member_left_at")
    private LocalDateTime leftAt;

    @Column(nullable = false, name = "member_status")
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "member_account_id")
    @JsonBackReference
    private Account account;

    public enum Role {
        PROJECT_MANAGER,
        SCRUM_MASTER,
        PRODUCT_OWNER,
        DEVELOPER,
        TESTER,
        VIEWER
    }
}