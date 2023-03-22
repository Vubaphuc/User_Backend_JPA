package com.example.bean.service;


import com.example.bean.exception.BadRequestException;
import com.example.bean.exception.NotFoundException;
import com.example.bean.model.Image;
import com.example.bean.model.response.FileResponse;
import com.example.bean.reponsitory.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private ImageRepository imageRepository;


    public FileResponse uploadFile(MultipartFile file) {
        validataFile(file);
        try {
            Image image = Image.builder()
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .createAt(LocalDateTime.now())
                    .build();

            imageRepository.save(image);
            return new  FileResponse("/api/v1/files" + image.getId());

        } catch (IOException e) {
            throw new RuntimeException("Có lỗi xảy ra");
        }
    }

    private void validataFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        // tên file không được để trống
        if (fileName == null || fileName.isEmpty()) {
            throw new BadRequestException("Tên File Không hợp lệ");
        }

        // type (loại) file có nằm trong danh sánh cho phép hay không
        // avatar.png, image.jpg => lấy ra đuôi file png và jpg
        String fileExtension = getFileExtension(fileName);
        if (!checkFileExtension(fileExtension)) {
            throw new BadRequestException("Type File Không hợp lệ");
        }

        // kích thước size có trong phạm  vi cho phép không
        double fileSize = (double) file.getSize() / 1048576;
        if (fileSize > 2) {
            throw new BadRequestException("Size File không được vượt quá 2MB");
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            return "";
        }
        return fileName.substring(lastIndex + 1);
    }

    private boolean checkFileExtension (String fileExtension) {
        List<String> fileExtensions = List.of("png","jpeg","jpg");
        return fileExtensions.contains(fileExtension);

    }



    public Image readFile(Integer id) {
        Image image = imageRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not Found Image with id = " + id);
        });
        return image;
    }

}
