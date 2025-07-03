package com.baconbao.JiScrum.service.impl;

import com.baconbao.JiScrum.dto.issue.IssueCreateDTO;
import com.baconbao.JiScrum.dto.issue.IssueDTO;
import com.baconbao.JiScrum.dto.issue.IssueFilter;
import com.baconbao.JiScrum.dto.issue.IssueUpdateDTO;
import com.baconbao.JiScrum.exception.ResourceNotFoundException;
import com.baconbao.JiScrum.mapper.IssueMapper;
import com.baconbao.JiScrum.model.Account;
import com.baconbao.JiScrum.model.Issue;
import com.baconbao.JiScrum.model.Member;
import com.baconbao.JiScrum.model.Project;
import com.baconbao.JiScrum.repository.IssueRepository;
import com.baconbao.JiScrum.service.AccountService;
import com.baconbao.JiScrum.service.IssueService;
import com.baconbao.JiScrum.service.MemberService;
import com.baconbao.JiScrum.service.ProjectService;
import com.baconbao.JiScrum.specification.IssueSpecifications;
import com.baconbao.JiScrum.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final MemberService memberService;
    private final ProjectService projectService;
    private final AccountService accountService;

    @Override
    public IssueDTO createIssue(IssueCreateDTO issueCreateDTO) {
        Project project = projectService.getProjectEntityById(issueCreateDTO.getProjectId());

        Member reporter = memberService.getMemberByProjectAndAccount(project.getId(), accountService.getPrincipal().getId());

        Issue issue = IssueMapper.toEntity(issueCreateDTO);
        issue.setId(IdGenerator.getGenerationId());
        issue.setProject(project);
        issue.setReporter(reporter);

        if(issueCreateDTO.getAssigneeId()!=null){
            Member assignee = memberService.getMemberEntityById(issueCreateDTO.getAssigneeId());
            issue.setAssignee(assignee);
        }

        return IssueMapper.toDto(issueRepository.save(issue));
    }

    @Override
    public IssueDTO updateIssue(IssueUpdateDTO issueUpdateDTO, Integer id) {
        Issue issue = getIssueEntityById(id);

        if (issueUpdateDTO.getTitle() != null) {
            issue.setTitle(issueUpdateDTO.getTitle());
        }

        if (issueUpdateDTO.getDescription() != null) {
            issue.setDescription(issueUpdateDTO.getDescription());
        }

        if (issueUpdateDTO.getPriority() != null) {
            issue.setPriority(issueUpdateDTO.getPriority());
        }

        if (issueUpdateDTO.getStatus() != null) {
            issue.setStatus(issueUpdateDTO.getStatus());
        }

        if (issueUpdateDTO.getDeadline() != null) {
            issue.setDeadline(issueUpdateDTO.getDeadline());
        }

        if(issueUpdateDTO.getResolvedAt() != null) {
            issue.setResolvedAt(issueUpdateDTO.getResolvedAt());
        }

        if (issueUpdateDTO.getAssigneeId() != null) {
            if (issueUpdateDTO.getAssigneeId() == 0) {
                issue.setAssignee(null);
            } else {
                Member newAssignee =
                        memberService.getMemberEntityById(issueUpdateDTO.getAssigneeId());
                issue.setAssignee(newAssignee);
            }
        }

        issue.setUpdatedAt(LocalDateTime.now());

        return IssueMapper.toDto(issueRepository.save(issue));
    }

    @Override
    public IssueDTO getIssueById(Integer id) {

        Issue issue = getIssueEntityById(id);

        return IssueMapper.toDto(issue);
    }

    @Override
    public Issue getIssueEntityById(Integer id) {

        return issueRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Issue with ID {} not found", id);
                    return new ResourceNotFoundException("Issue not found with id: " + id);
                });
    }

    @Override
    public void deleteIssue(Integer id) {

        Issue issue = getIssueEntityById(id);

        issueRepository.delete(issue);
    }

    @Override
    public Page<IssueDTO> filter(IssueFilter issueFilter, int page, int size) {
        Account me = accountService.getPrincipal();

        Specification<Issue> issueSpecification = IssueSpecifications.withFilter(issueFilter, me.getId());

        Pageable pageable = PageRequest.of(page, size);

        Page<Issue> issues = issueRepository.findAll(issueSpecification, pageable);

        return issues.map(IssueMapper::toDto);
    }
}
