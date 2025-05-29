package uz.duol.ecopharmwarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.department.dto.DepartmentDto;
import uz.duol.ecopharmwarehouse.module.department.service.DepartmentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wms/departments")
@PreAuthorize("hasAnyRole('ADMIN','USER') or hasRole('SUPER_ADMIN')")
@Tag(name = "Department", description = "Department API")
public class DepartmentController {
    private final DepartmentService service;

    @Operation(summary = "Get all departments",
            description = "Retrieve a paginated list of departments with optional search functionality",
            security = @SecurityRequirement(name = "bearAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved departments"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission")
            })
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('DEPARTMENT_READ') or hasRole('SUPER_ADMIN')")
    public Page<DepartmentDto> findAll(@RequestParam(value = "search", required = false) String search,
                                       @PageableDefault Pageable pageable) {
        return service.findAll(search, pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID",
            description = "Retrieve a department by its ID",
            security = @SecurityRequirement(name = "bearAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved department"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - department with given ID does not exist")
            })
    @PreAuthorize("hasAuthority('DEPARTMENT_READ') or hasRole('SUPER_ADMIN')")
    public DepartmentDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new department",
            description = "Create a new department with the provided details",
            security = @SecurityRequirement(name = "bearAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully created department"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission")
            })
    @PreAuthorize("hasAuthority('DEPARTMENT_CREATE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentDto create(@RequestBody @Valid DepartmentDto departmentDto) {
        return service.create(departmentDto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing department",
            description = "Update the details of an existing department by its ID",
            security = @SecurityRequirement(name = "bearAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated department"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - department with given ID does not exist")
            })
    @PreAuthorize("hasAuthority('DEPARTMENT_UPDATE') or hasRole('SUPER_ADMIN')")
    public DepartmentDto update(@PathVariable Long id, @RequestBody @Valid DepartmentDto departmentDto) {
        return service.update(id, departmentDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a department",
            description = "Delete a department by its ID",
            security = @SecurityRequirement(name = "bearAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted department"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - department with given ID does not exist")
            })
    @PreAuthorize("hasAuthority('DEPARTMENT_DELETE') or hasRole('SUPER_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
