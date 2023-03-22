package com.example.bean.controller;

import com.example.bean.model.dto.UserDto;
import com.example.bean.model.response.FileResponse;
import com.example.bean.model.request.CreateUserRequest;
import com.example.bean.model.request.UpdatePasswordRequest;
import com.example.bean.model.request.UpdateRequest;
import com.example.bean.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    // 1. Lấy danh sách users
    @GetMapping("/users")
    public ResponseEntity<?> getListUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    // 2. Tìm kiếm user theo tên
    @GetMapping("/search")
    public ResponseEntity<?> searchUserByName(@RequestParam(name = "name") String nameValue) {
        return ResponseEntity.ok(userService.getUserByName(nameValue));
    }


    // 3. Lấy chi tiết user theo id
    @GetMapping("/users/{id}")
    public ResponseEntity<?> searchUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //  4. Tạo mới user
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED) // trả về 201
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }


    // 5. Cập nhật thông tin user
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id,
                                        @RequestBody UpdateRequest request) {
        UserDto userDto = userService.updateUser(id, request);
        return ResponseEntity.ok(userDto);
    }


    // 6. Xóa user
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deteleUserById(@PathVariable int id) {
        userService.deleteUser(id);
    }

    // Cập nhật mật khẩu mới
    @PutMapping("/users/{id}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable int id,
                                            @RequestBody UpdatePasswordRequest request) {
        userService.updatePassword(id, request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Quên mật khẩu
    @PostMapping("/users/{id}/forgot-password")
    public ResponseEntity<?> updatePassword(@PathVariable int id) {
        String password = userService.forgotPassword(id);
        return ResponseEntity.ok(password);
    }


    // thay đổi avatar của user
    @PutMapping("/users/{id}/update-avatar")
    public ResponseEntity<?> updateAvatar(@ModelAttribute("file") MultipartFile file, @PathVariable Integer id) {
        FileResponse fileResponse = userService.updateAvatar(id,file);
        return ResponseEntity.ok(fileResponse);
    }

}
