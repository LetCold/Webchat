package com.phamthainguyen.webchat.models.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendResponse {

    private List<Friend> listFriend;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Friend {

        private Long id;
        private String name;
        private String imageName;
        private String msg;
    }
}
