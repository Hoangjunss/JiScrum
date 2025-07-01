package com.baconbao.JiScrum.service.impl;

import com.baconbao.JiScrum.dto.issue.IssueCreateDTO;
import com.baconbao.JiScrum.dto.issue.IssueDTO;
import com.baconbao.JiScrum.dto.issue.IssueUpdateDTO;
import com.baconbao.JiScrum.exception.ResourceNotFoundException;
import com.baconbao.JiScrum.mapper.IssueMapper;
import com.baconbao.JiScrum.model.Issue;
import com.baconbao.JiScrum.model.Member;
import com.baconbao.JiScrum.model.Project;
import com.baconbao.JiScrum.repository.IssueRepository;
import com.baconbao.JiScrum.service.IssueService;
import com.baconbao.JiScrum.service.MemberService;
import com.baconbao.JiScrum.service.ProjectService;
import com.baconbao.JiScrum.utils.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssueServiceImpl implements IssueService {
    private final IssueRepository issueRepository;
    private final MemberService memberService;
    private final ProjectService projectService;

    @Override
    public IssueDTO createIssue(IssueCreateDTO issueCreateDTO) {
        Project project = projectService.getProjectEntityById(issueCreateDTO.getProjectId());

        Member reporter = memberService.getMemberEntityById(issueCreateDTO.getReporterId());

        Issue issue = IssueMapper.toEntity(issueCreateDTO);
        issue.setId(IdGenerator.getGenerationId());
        issue.setProject(project);
        issue.setReporter(reporter);

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
}
