package com.baconbao.JiScrum.controller;

import com.baconbao.JiScrum.dto.APIResponse;
import com.baconbao.JiScrum.dto.member.MemberCreateDTO;
import com.baconbao.JiScrum.dto.member.MemberDTO;
import com.baconbao.JiScrum.dto.member.MemberUpdateDTO;
import com.baconbao.JiScrum.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing members.
 * Provides endpoints to create, retrieve, update, and delete member resources.
 */
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Member Controller", description = "Manage member resources including CRUD operations")
public class MemberController {

    private final MemberService memberService;

    /**
     * Creates a new member based on the provided data.
     *
     * @param dto     the member creation request data
     * @param request the HttpServletRequest for retrieving URI path
     * @return a ResponseEntity containing the created MemberDTO wrapped in APIResponse
     */
    @PostMapping
    @Operation(
            summary = "Create a new member",
            description = "Creates and saves a new member",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Member created successfully",
                            content = @Content(schema = @Schema(implementation = MemberDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    public ResponseEntity<APIResponse<MemberDTO>> createMember(@RequestBody MemberCreateDTO dto,
                                                               HttpServletRequest request) throws BadRequestException, BadRequestException {
        log.info("[MemberController] Received request to create a new member");
        MemberDTO memberDTO = memberService.createMember(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new APIResponse<>(
                true,
                "Member created successfully",
                memberDTO,
                null,
                request.getRequestURI()
        ));
    }

    /**
     * Retrieves the details of a member by its ID.
     *
     * @param id      the ID of the member to retrieve
     * @param request the HttpServletRequest for retrieving URI path
     * @return a ResponseEntity containing the MemberDTO wrapped in APIResponse
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get a member by ID",
            description = "Retrieves a member based on the specified ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Member retrieved successfully",
                            content = @Content(schema = @Schema(implementation = MemberDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Member not found")
            }
    )
    public ResponseEntity<APIResponse<MemberDTO>> getMemberById(@PathVariable Integer id,
                                                                HttpServletRequest request) {
        log.info("[MemberController] Retrieving member with ID: {}", id);
        MemberDTO memberDTO = memberService.getMemberById(id);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Member retrieved successfully",
                memberDTO,
                null,
                request.getRequestURI()
        ));
    }

    /**
     * Updates an existing member with the given ID using the provided data.
     *
     * @param id      the ID of the member to update
     * @param dto     the data used to update the member
     * @param request the HttpServletRequest for retrieving URI path
     * @return a ResponseEntity containing the updated MemberDTO wrapped in APIResponse
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing member",
            description = "Updates the member identified by the given ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Member updated successfully",
                            content = @Content(schema = @Schema(implementation = MemberDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Member not found")
            }
    )
    public ResponseEntity<APIResponse<MemberDTO>> updateMember(@PathVariable Integer id,
                                                               @RequestBody MemberUpdateDTO dto,
                                                               HttpServletRequest request) {
        log.info("[MemberController] Updating member with ID: {}", id);
        MemberDTO memberDTO = memberService.updateMember(id, dto);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Member updated successfully",
                memberDTO,
                null,
                request.getRequestURI()
        ));
    }

    /**
     * Deletes the member with the specified ID.
     *
     * @param id      the ID of the member to delete
     * @param request the HttpServletRequest for retrieving URI path
     * @return a ResponseEntity confirming the successful deletion
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a member by ID",
            description = "Deletes the member identified by the given ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Member deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Member not found")
            }
    )
    public ResponseEntity<APIResponse<Void>> deleteMember(@PathVariable Integer id,
                                                          HttpServletRequest request) {
        log.info("[MemberController] Deleting member with ID: {}", id);
        memberService.deleteMember(id);

        return ResponseEntity.ok(new APIResponse<>(
                true,
                "Member deleted successfully",
                null,
                null,
                request.getRequestURI()
        ));
    }
}
