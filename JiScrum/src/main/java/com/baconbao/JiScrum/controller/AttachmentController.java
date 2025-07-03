package com.baconbao.JiScrum.controller;

import com.baconbao.JiScrum.dto.APIResponse;
import com.baconbao.JiScrum.dto.attachment.AttachmentCreateDTO;
import com.baconbao.JiScrum.dto.attachment.AttachmentDTO;
import com.baconbao.JiScrum.dto.attachment.AttachmentUpdateDTO;
import com.baconbao.JiScrum.service.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Attachment Controller",
        description = "CRUD operations for Attachment resources")
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Upload a new attachment",
            description = "Creates a new attachment, optionally saving the file to storage",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Attachment created",
                            content = @Content(schema =
                            @Schema(implementation = AttachmentDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<APIResponse<AttachmentDTO>> createAttachment(
            @ModelAttribute AttachmentCreateDTO dto,
            HttpServletRequest request) {

        log.info("[AttachmentController] Uploading new attachment '{}'", dto.getName());
        AttachmentDTO saved = attachmentService.createAttachment(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new APIResponse<>(
                        true,
                        "Attachment created successfully",
                        saved,
                        null,
                        request.getRequestURI()
                ));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Update an attachment",
            description = "Updates attachment metadata and/or replaces the file",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Attachment updated",
                            content = @Content(schema =
                            @Schema(implementation = AttachmentDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Attachment not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<APIResponse<AttachmentDTO>> updateAttachment(
            @PathVariable Integer id,
            @ModelAttribute AttachmentUpdateDTO dto,
            HttpServletRequest request) {

        log.info("[AttachmentController] Updating attachment id = {}", id);
        AttachmentDTO updated = attachmentService.updateAttachment(dto, id);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Attachment updated successfully",
                updated,
                null,
                request.getRequestURI()
        ));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get attachment by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK",
                            content = @Content(schema =
                            @Schema(implementation = AttachmentDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Attachment not found")
            }
    )
    public ResponseEntity<APIResponse<AttachmentDTO>> getAttachmentById(
            @PathVariable Integer id,
            HttpServletRequest request) {

        log.debug("[AttachmentController] Fetching attachment id = {}", id);
        AttachmentDTO dto = attachmentService.getAttachmentById(id);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Attachment fetched successfully",
                dto,
                null,
                request.getRequestURI()
        ));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete attachment",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Attachment deleted"),
                    @ApiResponse(responseCode = "404", description = "Attachment not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> deleteAttachment(@PathVariable Integer id, HttpServletRequest request) {
        log.warn("[AttachmentController] Deleting attachment id = {}", id);
        attachmentService.deleteAttachment(id);
        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Issue deleted successfully",
                null,
                null,
                request.getRequestURI()
        ));
    }
}
