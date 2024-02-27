package com.phamthainguyen.webchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phamthainguyen.webchat.models.request.FindFriendRequest;
import com.phamthainguyen.webchat.models.request.FriendRequest;
import com.phamthainguyen.webchat.models.response.FriendResponse;
import com.phamthainguyen.webchat.models.response.StatusResponse;
import com.phamthainguyen.webchat.service.FriendService;

@RestController
@RequestMapping("/api/user/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;
    
    @GetMapping
    public FriendResponse getFriend(){
        return friendService.getFriend();
    }

    @GetMapping("/request")
    public FriendResponse getRequestFriend(){
        return friendService.getRequestFriend();
    }
    @PostMapping("/request")
    public StatusResponse posttRequestFriend(@RequestBody FriendRequest request){
        System.out.println(request.toString());
        return friendService.submitRequestFriend(request.getIdUser());
    }


    @DeleteMapping("/{id}")
    public StatusResponse deleteFriend(@PathVariable Long id){
        return friendService.deleteFriend(id);
    }

    @PostMapping()
    public StatusResponse postAddFriend(@RequestBody FriendRequest request){
        return friendService.addRequestFriend(request.getIdUser());
    }

    @PostMapping("/find")
    public FriendResponse getFindFriend(@RequestBody FindFriendRequest request){
        return friendService.getFindFriend(request);
    }
}
