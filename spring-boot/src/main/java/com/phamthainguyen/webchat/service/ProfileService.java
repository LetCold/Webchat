package com.phamthainguyen.webchat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phamthainguyen.webchat.models.entity.User;
import com.phamthainguyen.webchat.models.request.EditProfileRequest;
import com.phamthainguyen.webchat.models.response.ProfileResponse;
import com.phamthainguyen.webchat.models.response.StatusResponse;
import com.phamthainguyen.webchat.responsitory.UserRepository;

@Service
public class ProfileService {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileService fileService;

    public ProfileResponse getProfile() {
        User user = this.authService.getUser();
        if(user==null) return ProfileResponse.builder().build();
        return ProfileResponse.builder().email(user.getEmail()).fullname(user.getFullName())
                .imageName(user.getImageName()).build();
    }

    public StatusResponse editProfile(EditProfileRequest request) {
        User user = this.authService.getUser();
        if(user==null) {
            return StatusResponse.builder().status("fall").build();
        }
        user.setFullName(request.getFullName());
        if (request.getImageName() == null) {
            userRepository.save(user);
            return StatusResponse.builder().status("success").build();
        }
        if (user.getImageName() != null) {
            fileService.removeImage(user.getImageName());
            user.setImageName(request.getImageName());

        } else {
            user.setImageName(request.getImageName());
        }
        userRepository.save(user);
        return StatusResponse.builder().status("success").build();
    }
}
