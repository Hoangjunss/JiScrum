package com.baconbao.JiScrum.repository;

import com.baconbao.JiScrum.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
}
