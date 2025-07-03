package com.baconbao.JiScrum.specification;

import com.baconbao.JiScrum.dto.issue.IssueFilter;
import com.baconbao.JiScrum.model.Issue;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IssueSpecifications {
    public static Specification<Issue> withFilter(IssueFilter f, Integer meId) {
        return Specification
                .where(titleContains(f.getTitle()))
                .and(priorityEquals(f.getPriority()))
                .and(statusEquals(f.getStatus()))
                .and(projectIdIn(f.getProjectId()))
                .and(reporterIdEquals(f.getReporterId() ? meId : null))
                .and(assigneeIdEquals(meId))
                .and(deadlineBetween(f.getDeadlineFrom(), f.getDeadlineTo()));
    }

    private static Specification<Issue> titleContains(String title) {
        return (title == null || title.isBlank())
                ? null
                : (root, q, cb) ->
                cb.like(cb.lower(root.get("title")),
                        "%" + title.toLowerCase().trim() + "%");
    }

    private static Specification<Issue> priorityEquals(String priority) {
        if (priority == null) return null;
        try {
            Issue.IssuePriority p = Issue.IssuePriority.valueOf(priority.trim().toUpperCase());
            return (root, q, cb) -> cb.equal(root.get("priority"), p);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static Specification<Issue> statusEquals(String status) {
        if (status == null) return null;
        try {
            Issue.IssueStatus s = Issue.IssueStatus.valueOf(status.trim().toUpperCase());
            return (root, q, cb) -> cb.equal(root.get("status"), s);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static Specification<Issue> projectIdIn(String projectIds) {
        if (projectIds == null || projectIds.isBlank()) return null;

        List<Integer> ids = Arrays.stream(projectIds.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .map(Integer::valueOf)
                .toList();

        return (root, q, cb) ->
                root.get("project").get("id").in(ids);
    }

    private static Specification<Issue> reporterIdEquals(Integer reporterId) {
        return (reporterId == null)
                ? null
                : (root, q, cb) ->
                cb.equal(root.get("reporter").get("id"), reporterId);
    }

    private static Specification<Issue> assigneeIdEquals(Integer assigneeId) {
        return (assigneeId == null)
                ? null
                : (root, q, cb) ->
                cb.equal(root.get("assignee").get("account").get("id"), assigneeId);
    }

    private static Specification<Issue> deadlineBetween(
            LocalDateTime from, LocalDateTime to) {

        if (from == null && to == null) return null;

        return (root, q, cb) -> {
            Path<LocalDateTime> deadline = root.get("deadline");
            List<Predicate> predicates = new ArrayList<>(2);

            if (from != null) predicates.add(cb.greaterThanOrEqualTo(deadline, from));
            if (to   != null) predicates.add(cb.lessThanOrEqualTo(deadline, to));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
