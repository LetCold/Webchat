package com.phamthainguyen.webchat.component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.phamthainguyen.webchat.models.entity.User;
import com.phamthainguyen.webchat.models.request.SendMessageRequest;
import com.phamthainguyen.webchat.service.AuthService;
import com.phamthainguyen.webchat.service.MessageService;
import com.phamthainguyen.webchat.util.JsonConven;

@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthService authService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        Map<String, Object> attributes = session.getAttributes();
        String token = (String) attributes.get("token");
        User user = authService.getUserByToken(token);
        messageService.addSessionAndId(session, user.getId());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException, InterruptedException {
        SendMessageRequest sendMessageRequest = JsonConven.convertStringToObject(message.getPayload());
        if (sendMessageRequest != null) {
            List<WebSocketSession> listWebsocketSession = messageService.getSessionById(sendMessageRequest.getReceiver());
            sendMessageRequest.setSender((Long)session.getAttributes().get("id"));
            messageService.sendMessage(listWebsocketSession, sendMessageRequest );
        }
    }

}
