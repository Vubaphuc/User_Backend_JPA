package com.example.bean.model.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateRequest {
   @NotNull(message = "Name không được để trống")
   private String name;
   @NotNull(message = "Phone không được để trống")
   private String phone;
   @NotNull(message = "Address không được để trống")
   private String address;

}
