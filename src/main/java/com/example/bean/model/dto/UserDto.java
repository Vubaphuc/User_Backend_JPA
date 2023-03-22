package com.example.bean.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String avatar;
}
