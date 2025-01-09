package uz.pdp.transactionreports.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;
import uz.pdp.transactionreports.dto.UserCRUDDto;
import uz.pdp.transactionreports.dto.UserUpdateDto;
import uz.pdp.transactionreports.entity.User;
import uz.pdp.transactionreports.service.UserService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserCRUDDto user) {
        return ResponseEntity.ok(userService.create(user));
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
