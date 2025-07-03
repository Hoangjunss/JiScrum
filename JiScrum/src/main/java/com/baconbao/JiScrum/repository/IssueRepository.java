package com.baconbao.JiScrum.repository;

import com.baconbao.JiScrum.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IssueRepository extends JpaRepository<Issue, Integer>, JpaSpecificationExecutor<Issue> {
}
