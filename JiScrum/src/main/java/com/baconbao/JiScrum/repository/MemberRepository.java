package com.baconbao.JiScrum.repository;

import com.baconbao.JiScrum.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Integer> {

    @Query(value = """
            SELECT m.*
            FROM member m
            WHERE m.member_account_id = :projectId AND m.member_project_id = :accountId""",
            nativeQuery = true)
    Optional<Member> findByProjectIdAndAccountId(@Param("projectId") Integer projectId, @Param("accountId") Integer accountId);
}
