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
import uz.duol.ecopharmwarehouse.module.address.dto.AddressDTO;
import uz.duol.ecopharmwarehouse.module.address.service.AddressService;

@RestController
@RequestMapping("/api/v1/wms/address")
@RequiredArgsConstructor
@Tag(name = "Address endpoint")
@PreAuthorize("hasAnyRole('ADMIN','MANAGER','USER') or hasRole('SUPER_ADMIN')")
public class AddressController {
    private final AddressService addressService;

    @Operation(
            summary = "Create address",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('ADDRESS_CREATE') or hasRole('SUPER_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressDTO create(@Valid @RequestBody AddressDTO dto) {
        return addressService.create(dto);
    }

    @Operation(
            summary = "Get address by id",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - data not found"),
            }
    )
    @PreAuthorize("hasAuthority('ADDRESS_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public AddressDTO getAddress(@PathVariable Long id) {
        return addressService.findById(id);
    }

    @Operation(
            summary = "Update address",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid data"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('ADDRESS_UPDATE') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public AddressDTO updateAddress(@PathVariable Long id, @Valid @RequestBody AddressDTO dto) {
        return addressService.update(id, dto);
    }

    @Operation(
            summary = "Delete address",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Success"),
                    @ApiResponse(responseCode = "400", description = "Bad request - invalid id"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission"),
                    @ApiResponse(responseCode = "404", description = "Not found - Data not found"),
            }
    )
    @PreAuthorize("hasAuthority('ADDRESS_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
    }

    @Operation(
            summary = "Get address list",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized - bad credential"),
                    @ApiResponse(responseCode = "403", description = "Access denied - bad role or permission"),
            }
    )
    @PreAuthorize("hasAuthority('ADDRESS_GET') or hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public Page<AddressDTO> getAll(@RequestParam(value = "search", required = false) String search,
                                         @PageableDefault Pageable pageable) {
        return addressService.findAll(search, pageable);
    }
}
