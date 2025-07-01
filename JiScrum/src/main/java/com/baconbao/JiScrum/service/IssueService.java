package com.baconbao.JiScrum.service;

import com.baconbao.JiScrum.dto.issue.IssueCreateDTO;
import com.baconbao.JiScrum.dto.issue.IssueDTO;
import com.baconbao.JiScrum.dto.issue.IssueUpdateDTO;
import com.baconbao.JiScrum.model.Issue;

public interface IssueService {
    IssueDTO createIssue(IssueCreateDTO issueCreateDTO);
    IssueDTO updateIssue(IssueUpdateDTO issueUpdateDTO, Integer id);
    IssueDTO getIssueById(Integer id);
    Issue getIssueEntityById(Integer id);
    void deleteIssue(Integer id);
}
