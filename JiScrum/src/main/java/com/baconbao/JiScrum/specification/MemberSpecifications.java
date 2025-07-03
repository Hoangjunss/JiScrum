package com.baconbao.JiScrum.specification;

import com.baconbao.JiScrum.dto.member.MemberFilter;
import com.baconbao.JiScrum.model.Member;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberSpecifications {
    public static Specification<Member> withFilter(MemberFilter f) {
        return Specification
                .where(projectIdEquals(f.getProjectId()))
                .and(roleEquals(f.getRole()))
                .and(joinDateBetween(f.getStartJoinDate(), f.getEndJoinDate()))
                .and(statusEquals(f.getStatus()));
    }

    private static Specification<Member> projectIdEquals(Integer projectId) {
        return (projectId == null)
                ? null
                : (root, q, cb) ->
                cb.equal(root.get("project").get("id"), projectId);
    }

    private static Specification<Member> roleEquals(String role) {
        if (role == null) return null;
        try {
            Member.Role r = Member.Role.valueOf(role.trim().toUpperCase());
            return (root, q, cb) -> cb.equal(root.get("role"), r);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private static Specification<Member> joinDateBetween(
            LocalDateTime from, LocalDateTime to) {

        if (from == null && to == null) return null;

        return (root, q, cb) -> {
            Path<LocalDateTime> joinedAt = root.get("joinedAt");
            List<Predicate> ps = new ArrayList<>(2);

            if (from != null) ps.add(cb.greaterThanOrEqualTo(joinedAt, from));
            if (to   != null) ps.add(cb.lessThanOrEqualTo(joinedAt, to));

            return cb.and(ps.toArray(new Predicate[0]));
        };
    }

    private static Specification<Member> statusEquals(Boolean status) {
        return (status == null)
                ? null
                : (root, q, cb) -> cb.equal(root.get("status"), status);
    }
}
