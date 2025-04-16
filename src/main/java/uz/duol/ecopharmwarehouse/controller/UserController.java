package uz.duol.ecopharmwarehouse.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.duol.ecopharmwarehouse.module.users.dto.UserDTO;
import uz.duol.ecopharmwarehouse.module.users.service.UserService;

@RestController
@RequestMapping("/api/v1/wms/user")
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/list")
    public Page<UserDTO> getAllUserPermission(@RequestParam(value = "search", required = false) String search, @PageableDefault Pageable pageable) {
        return userService.findAll(search, pageable);
    }

    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable String id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO create(@RequestBody UserDTO user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserDTO update(@PathVariable String id, @RequestBody UserDTO user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }

}
