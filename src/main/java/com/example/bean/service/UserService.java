package com.example.bean.service;

import com.example.bean.database.UserDB;
import com.example.bean.model.dto.UserDto;
import com.example.bean.exception.BadRequestException;
import com.example.bean.exception.NotFoundException;
import com.example.bean.model.mapper.UserMapper;
import com.example.bean.model.User;
import com.example.bean.model.response.FileResponse;
import com.example.bean.model.request.CreateUserRequest;
import com.example.bean.model.request.UpdatePasswordRequest;
import com.example.bean.model.request.UpdateRequest;
import com.example.bean.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private MailService mailService;



    // 1. Lấy danh sách users
    public List<UserDto> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    // 2. Tìm kiếm user theo tên
    public List<UserDto> getUserByName(String name) {
        return userRepository.findUserByName(name)
                .stream()
                .filter(user -> user.getName().equalsIgnoreCase(name))
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    // 3. Lấy chi tiết user theo id
    public UserDto getUserById(int id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> {
            throw new NotFoundException("Not Found user with id = " + id);
        });

        return UserMapper.toUserDto(user);
    }

    // 4. Tạo mới user

    public UserDto createUser(CreateUserRequest request) {

        Random rd = new Random();

        User user = new User();

        user.setId(rd.nextInt(100));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setPassword(request.getPassword());

        User user1 =  userRepository.save(user);

        return UserMapper.toUserDto(user1);
    }


    // 5. Cập nhật thông tin user
    public UserDto updateUser(int id, UpdateRequest request) {
        User user = userRepository.findUserById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        User user1 = userRepository.save(user);
        return UserMapper.toUserDto(user1);
    }
    


    // 6. Xóa user
    public void deleteUser(int id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        userRepository.deleteById(user.getId());
    }

    // Cập nhật password mới
    public void updatePassword(int id, UpdatePasswordRequest request) {
        // Kiểm tra có tồn tại hay không
        User user = userRepository.findUserById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        // Kiểm tra oldPassword có đúng không
        if (!user.getPassword().equals(request.getOldPassword())) {
            throw new BadRequestException("old password is incorrect!");
        }

        // Kiểm tra oldPassword có = newPassword không
        if (request.getNewPassword().equals(request.getOldPassword())) {
            throw new BadRequestException("old password and new password cannot be the same!");
        }

        // Cập nhật newPassword cho user tương ứng
        user.setPassword(request.getNewPassword());
        userRepository.save(user);
    }

    // Quên mật khẩu
    public String forgotPassword(int id) {
        // Kiểm tra user có tồn tại hay không
        User user = userRepository.findUserById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        // Random chuỗi password mới cho user (100 -> 999)
        Random rd = new Random();
        String newPassword = String.valueOf(rd.nextInt(900) + 100);

        // Lấy thông tin của user và đặt lại password mới cho user
        user.setPassword(newPassword);

        // gửi mail
        mailService.sendMail(
                user.getEmail(),
                "Thay Đổi Mật Khẩu",
                "Mật khẩu mới " + newPassword
        );

        // Trả về thông tin password mới
        return newPassword;
    }

    public FileResponse updateAvatar(Integer id, MultipartFile file) {
        User user = userRepository.findUserById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        // Upload file
        FileResponse fileResponse = fileService.uploadFile(file);

        // Set lại avatar của user
        user.setAvatar(fileResponse.getUrl());

        return fileResponse;
    }


}
