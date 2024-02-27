package com.phamthainguyen.webchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.phamthainguyen.webchat.models.request.EditProfileRequest;
import com.phamthainguyen.webchat.models.response.ProfileResponse;
import com.phamthainguyen.webchat.models.response.StatusResponse;
import com.phamthainguyen.webchat.service.FileService;
import com.phamthainguyen.webchat.service.ProfileService;

@RestController
@RequestMapping("/api/user/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FileService fileService;

    @GetMapping
    public ProfileResponse getProfile(){
        return profileService.getProfile();
    }

    @PutMapping()
    public StatusResponse putProfile(@RequestBody EditProfileRequest request) {
        return profileService.editProfile(request);
    }

    @PostMapping("/upload")
    public StatusResponse postUpload(@RequestParam("file") MultipartFile file){
        return fileService.saveImage(file);
    }

}
