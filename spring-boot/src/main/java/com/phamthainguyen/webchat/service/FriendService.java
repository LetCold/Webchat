package com.phamthainguyen.webchat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phamthainguyen.webchat.models.entity.User;
import com.phamthainguyen.webchat.models.request.FindFriendRequest;
import com.phamthainguyen.webchat.models.response.FriendResponse;
import com.phamthainguyen.webchat.models.response.FriendResponse.Friend;
import com.phamthainguyen.webchat.models.response.StatusResponse;
import com.phamthainguyen.webchat.responsitory.UserRepository;
import com.phamthainguyen.webchat.util.JsonConven;

@Service
public class FriendService {
    
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    public FriendResponse getFriend() {
        User user = authService.getUser();
        String listIdString = user.getIdFriend();
        List<Long> listId = JsonConven.convertJsonToListLong(listIdString);
        List<Friend> listFriend = new ArrayList<>();
        for (Long id : listId) {
            User friendUser = userRepository.findById(id).orElse(null);
            if (friendUser != null) {
                Friend friend = new FriendResponse().new Friend(friendUser.getId(),friendUser.getFullName(), friendUser.getImageName(), null);
                listFriend.add(friend);
            }
        }
        return FriendResponse.builder()
                .listFriend(listFriend)
                .build();
    }

    public StatusResponse deleteFriend(Long idUser){
        User friend = userRepository.findById(idUser).orElse(null);
        if(friend == null){
            return StatusResponse.builder().status("fall").build();
        }
        User user = authService.getUser();
        removeFriend(idUser, user);
        removeFriend(user.getId(), friend);
        return StatusResponse.builder().status("success").build();
    }

    public StatusResponse addRequestFriend(Long idUser){
        User friend = userRepository.findById(idUser).orElse(null);
        if(friend == null){
            return StatusResponse.builder().status("fall1").build();
        }
        User user = authService.getUser();
        String listIdRequestFriend = friend.getIdRequestFriend();
        List<Long> listId = JsonConven.convertJsonToListLong(listIdRequestFriend);
        if(!IsFriend(idUser, user)){
            listId.add(user.getId());
            listIdRequestFriend = JsonConven.convertListToJson(listId);
            friend.setIdRequestFriend(listIdRequestFriend);
            userRepository.save(friend);
            return StatusResponse.builder().status("success").build();
        }
        return StatusResponse.builder().status("fall2").build();
    }

    public StatusResponse submitRequestFriend(Long idUser){
        User user = authService.getUser();
        if(IsRequestFriend(idUser, user)){
            User user2 = userRepository.findById(idUser).orElse(null);
            if(user2 == null){
                return StatusResponse.builder().status("fall").build();
            }
            this.removeRequestFriend(idUser, user);
            this.removeRequestFriend(user.getId(), user2);
            this.addFriend(idUser, user);
            this.addFriend(user.getId(), user2);
            return StatusResponse.builder().status("success").build();
        }
        return StatusResponse.builder().status("fall").build();
    }

    public FriendResponse getRequestFriend(){
        User user = authService.getUser();
        if(user == null){
            return FriendResponse.builder().build();
        }
        String listIdRequestFriend = user.getIdRequestFriend();
        List<Long> listId = JsonConven.convertJsonToListLong(listIdRequestFriend);
        List<Friend> listRequestFriend = new ArrayList<>();
        for(Long id: listId){
            User requestFriend = userRepository.findById(id).orElse(null);
            if(requestFriend != null){
                Friend friend = new FriendResponse().new Friend(requestFriend.getId(),requestFriend.getFullName(), requestFriend.getImageName(), null);
                listRequestFriend.add(friend);
            }
        }
        return FriendResponse.builder().listFriend(listRequestFriend).build();
    }

    public FriendResponse getFindFriend(FindFriendRequest request){
        User user = authService.getUser();
        if(user == null){
            return FriendResponse.builder().build();
        }
        if(user.getEmail().equals(request.getEmail())){
            return FriendResponse.builder().build();
        }
        User friend = userRepository.findByEmail(request.getEmail()).orElse(null);
        if(friend ==null){
            return FriendResponse.builder().build();
        }
        if(IsRequestFriend(friend.getId(), user)){
            return FriendResponse.builder().build();
        }
        List<Friend> listFindFriend = new ArrayList<>();
        listFindFriend.add(new FriendResponse().new Friend(friend.getId(),friend.getFullName(), friend.getImageName(), null));
        return FriendResponse.builder().listFriend(listFindFriend).build();
    }

    public boolean IsFriend(Long id, User user){
        String listIdString = user.getIdFriend();
        List<Long> listId = JsonConven.convertJsonToListLong(listIdString);
        return listId.contains(id);
    }
    public boolean IsRequestFriend(Long id, User user){
        String listIdString = user.getIdRequestFriend();
        List<Long> listId = JsonConven.convertJsonToListLong(listIdString);
        return listId.contains(id);
    }
    public void addFriend(Long idUser, User user){
        String listIdFriend = user.getIdFriend();
        List<Long> listId = JsonConven.convertJsonToListLong(listIdFriend);
        if(!this.IsFriend(idUser, user)){
            listId.add(idUser);
            listIdFriend = JsonConven.convertListToJson(listId);
            user.setIdFriend(listIdFriend);
            userRepository.save(user);
        }
    }

    public void removeFriend(Long idUser, User user){
        String listIdFriend = user.getIdFriend();
        List<Long> listId = JsonConven.convertJsonToListLong(listIdFriend);
        if(this.IsFriend(idUser, user)){
            listId.removeIf(id -> id == idUser);
            listIdFriend = JsonConven.convertListToJson(listId);
            user.setIdFriend(listIdFriend);
            userRepository.save(user);
        }
    }

    public void removeRequestFriend(Long idUser, User user){
        String listIdRequestFriend = user.getIdRequestFriend();
        List<Long> listId = JsonConven.convertJsonToListLong(listIdRequestFriend);
        if(!this.IsFriend(idUser, user)){
            listId.removeIf(id -> id == idUser);
            listIdRequestFriend = JsonConven.convertListToJson(listId);
            user.setIdRequestFriend(listIdRequestFriend);
            userRepository.save(user);
        }
    }
}
