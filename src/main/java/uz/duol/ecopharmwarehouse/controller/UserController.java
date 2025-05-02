package uz.duol.ecopharmwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;
import uz.duol.ecopharmwarehouse.module.users.dto.UserUpdateDto;
import uz.duol.ecopharmwarehouse.module.users.service.UserService;

@RestController
@RequestMapping("/api/v1/wms/user")
@PreAuthorize("hasAnyRole('ADMIN','USER') or hasRole('SUPER_ADMIN')")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('USER_READ') or hasRole('SUPER_ADMIN')")
    @GetMapping("/list")
    public Page<UserDTO> getAllUserPermission(@RequestParam(value = "search", required = false) String search, @PageableDefault Pageable pageable) {
        return userService.findAll(search, pageable);
    }

    @PreAuthorize("hasAuthority('USER_READ') or hasRole('SUPER_ADMIN')")
    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable String id) {
        return userService.findById(id);
    }

    @PreAuthorize("hasAuthority('USER_CREATE') or hasRole('SUPER_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }

    @PreAuthorize("hasAuthority('USER_UPDATE') or hasRole('SUPER_ADMIN')")
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id, @RequestBody UserUpdateDto user) {
        return userService.update(id, user);
    }

    @PreAuthorize("hasAuthority('USER_DELETE') or hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

}
