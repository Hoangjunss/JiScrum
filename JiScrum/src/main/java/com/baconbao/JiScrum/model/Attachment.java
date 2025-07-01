package com.baconbao.JiScrum.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attachment")
public class Attachment {
    @Id
    @Column(name = "attachment_id", nullable = false)
    private Integer id;

    @Column(name = "attachment_name", length = 255)
    private String name;

    @Column(name = "attachment_path", length = 500)
    private String path;

    @Column(name = "attachment_size")
    private Long size;

    @Column(name = "attachment_type", length = 50)
    private String type;

    @ManyToOne
    @JoinColumn(name = "attachment_issue_id")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "attachment_uploaded_by")
    private Member uploadedBy;

    @Column(name = "attachment_uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;
}
