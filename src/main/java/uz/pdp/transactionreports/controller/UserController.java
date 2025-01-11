package uz.pdp.transactionreports.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.transactionreports.dto.UserCreateReadDto;
import uz.pdp.transactionreports.dto.UserLoginDto;
import uz.pdp.transactionreports.dto.UserUpdateDto;
import uz.pdp.transactionreports.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok(userService.login(userLoginDto));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateReadDto user) {
        return ResponseEntity.ok(userService.register(user));
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody UserUpdateDto user) {
        return ResponseEntity.ok(userService.update(user));
    }
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted!");
    }
    @DeleteMapping("/delete/username/{username}")
    public ResponseEntity<?> deleteByUsername(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.ok("User deleted!");
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("/get/id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getById(id));
    }
    @GetMapping("/get/username/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

}
