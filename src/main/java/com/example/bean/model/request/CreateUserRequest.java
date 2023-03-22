package com.example.bean.model.request;

import com.example.bean.model.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateUserRequest {
    @NotNull(message = "Name không được để trống")
    private String name;
    @NotNull(message = "Email không được để trống")
    private String email;
    private String phone;
    private String address;
    @NotNull(message = "Password không được để trống")
    private String password;

}
