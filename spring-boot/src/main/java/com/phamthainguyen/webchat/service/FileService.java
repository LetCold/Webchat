package com.phamthainguyen.webchat.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.phamthainguyen.webchat.models.response.StatusResponse;

@Service
public class FileService {
    @Value("${upload.directory}")
    private String uploadDirectory;

    public StatusResponse saveImage(MultipartFile file){
        try{
        Path targetPath = Paths.get(uploadDirectory, file.getOriginalFilename());
        Files.copy(file.getInputStream(), targetPath);
        return StatusResponse.builder().status("success").build();
        }catch(IOException e){
            return StatusResponse.builder().status("fall").build();
        }
    }

    public boolean removeImage(String name){
        try {
            Path path = Paths.get(uploadDirectory+name);
            Files.delete(path);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting file: " + e.getMessage());
            return false;
        }
    }
}
