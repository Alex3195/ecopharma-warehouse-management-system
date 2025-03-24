package uz.duol.ecopharmwarehouse.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequestMapping("/api/v1/test")
public class TestController {
    @GetMapping("/test")
    public String test() {
        return "Test";
    }

}
