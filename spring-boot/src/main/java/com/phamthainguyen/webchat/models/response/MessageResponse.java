package com.phamthainguyen.webchat.models.response;

import java.util.List;

import com.phamthainguyen.webchat.models.entity.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageResponse {
    private List<Message> listMessage;
}
