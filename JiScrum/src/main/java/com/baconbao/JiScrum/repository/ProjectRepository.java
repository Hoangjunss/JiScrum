package com.baconbao.JiScrum.repository;

import com.baconbao.JiScrum.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProjectRepository extends JpaRepository<Project,Integer>, JpaSpecificationExecutor<Project> {
}
