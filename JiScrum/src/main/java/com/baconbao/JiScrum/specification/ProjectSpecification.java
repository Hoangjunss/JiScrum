package com.baconbao.JiScrum.specification;

import com.baconbao.JiScrum.model.Account;
import com.baconbao.JiScrum.model.Member;
import com.baconbao.JiScrum.model.Project;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecification {
    public static Specification<Project> belongsToAccount(Account account) {
        return (root, query, cb) -> {
            Join<Project, Member> memberJoin = root.join("members", JoinType.INNER);
            return cb.equal(memberJoin.get("account").get("id"), account.getId());
        };
    }

    public static Specification<Project> hasName(String name) {
        return (name == null || name.isBlank())
                ? null
                : (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Project> hasStatus(Project.ProjectStatus status) {
        return (status == null)
                ? null
                : (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Project> hasOwnerId(Integer ownerId) {
        return (ownerId == null)
                ? null
                : (root, query, cb) ->
                cb.equal(root.join("owner", JoinType.INNER).get("id"), ownerId);
    }
}
