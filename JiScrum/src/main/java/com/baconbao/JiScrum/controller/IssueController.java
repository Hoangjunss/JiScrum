package com.baconbao.JiScrum.controller;

import com.baconbao.JiScrum.dto.APIResponse;
import com.baconbao.JiScrum.dto.issue.IssueCreateDTO;
import com.baconbao.JiScrum.dto.issue.IssueDTO;
import com.baconbao.JiScrum.dto.issue.IssueUpdateDTO;
import com.baconbao.JiScrum.service.IssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Issue Controller", description = "CRUD operations for Issue resources")
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    @Operation(
            summary = "Create a new issue",
            description = "Creates and saves a new issue ticket",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Issue created",
                            content = @Content(schema = @Schema(implementation = IssueDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<APIResponse<IssueDTO>> createIssue( @RequestBody IssueCreateDTO dto,
                                                             HttpServletRequest request) {
        log.info("[IssueController] Create Issue request received, title = {}", dto.getTitle());
        IssueDTO saved = issueService.createIssue(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        true,
                        "Issue created successfully",
                        saved,
                        null,
                        request.getRequestURI()
                ));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing issue",
            description = "Updates issue attributes (partial update)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Issue updated",
                            content = @Content(schema = @Schema(implementation = IssueDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Issue not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<APIResponse<IssueDTO>> updateIssue(
            @PathVariable Integer id,
            @RequestBody IssueUpdateDTO dto,
            HttpServletRequest request) {

        log.info("[IssueController] Update Issue request id = {}", id);
        IssueDTO updated = issueService.updateIssue(dto, id);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Issue updated successfully",
                updated,
                null,
                request.getRequestURI()
        ));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get issue by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema = @Schema(implementation = IssueDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Issue not found")
            }
    )
    public ResponseEntity<APIResponse<IssueDTO>> getIssueById(@PathVariable Integer id,
                                                              HttpServletRequest request) {
        log.debug("[IssueController] Get Issue id = {}", id);
        IssueDTO dto = issueService.getIssueById(id);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Issue fetched successfully",
                dto,
                null,
                request.getRequestURI()
        ));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete issue by id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Issue deleted"),
                    @ApiResponse(responseCode = "404", description = "Issue not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> deleteIssue(@PathVariable Integer id, HttpServletRequest request) {
        log.warn("[IssueController] Delete Issue id = {}", id);
        issueService.deleteIssue(id);
        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Issue deleted successfully",
                null,
                null,
                request.getRequestURI()
        ));
    }
}