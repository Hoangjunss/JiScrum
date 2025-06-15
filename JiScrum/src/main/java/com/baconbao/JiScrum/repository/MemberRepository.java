package com.baconbao.JiScrum.repository;

import com.baconbao.JiScrum.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Integer> {
}
