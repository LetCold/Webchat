package com.phamthainguyen.webchat.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.phamthainguyen.webchat.models.entity.Message;
import com.phamthainguyen.webchat.models.entity.User;
import com.phamthainguyen.webchat.models.request.SendMessageRequest;
import com.phamthainguyen.webchat.models.response.MessageResponse;
import com.phamthainguyen.webchat.responsitory.MessageRepository;
import com.phamthainguyen.webchat.util.JsonConven;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AuthService authService;

    private Map<WebSocketSession, Long> sessions = new ConcurrentHashMap<>();

    public List<WebSocketSession> getSessionById(Long idUser) {
        return sessions.entrySet().stream()
                .filter(entry -> entry.getValue().equals(idUser))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public void addSessionAndId(WebSocketSession webSocketSession, Long id) {
        webSocketSession.getAttributes().put("id", id);
        sessions.put(webSocketSession, id);
    }

    public void sendMessage(List<WebSocketSession> webSocketSessions, SendMessageRequest sendMessageRequest) {
        Long senderId = 0L;
        if (webSocketSessions == null) return;
    
        Iterator<WebSocketSession> iterator = webSocketSessions.iterator();
        while (iterator.hasNext()) {
            WebSocketSession session = iterator.next();
            try {
                if (session.isOpen()) {
                    senderId = (Long) session.getAttributes().get("id");
                    session.sendMessage(new TextMessage(JsonConven.convertObjectToString(sendMessageRequest)));
                } else {
                    iterator.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    
        if (senderId != null && senderId != 0L) {
            addMessage(sendMessageRequest.getContent(), sendMessageRequest.getSender(), sendMessageRequest.getReceiver());
        }
    }
    public MessageResponse getMessage(Long id) {
        User user = authService.getUser();
        List<Message> senderMessage = messageRepository.findTop10MessagesBySenderAndReceiver(user.getId(), id);
        List<Message> receiverMessage = messageRepository.findTop10MessagesByReceiverAndSender(id, user.getId());
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(senderMessage);
        allMessages.addAll(receiverMessage);
        Collections.sort(allMessages, (m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

        return MessageResponse.builder().listMessage(allMessages).build();
    }

    public void addMessage(String msg, Long sender, Long receiver ) {
        Message message = Message.builder()
                .sender(sender)
                .receiver(receiver)
                .content(msg)
                .timestamp(new Date())
                .build();
        messageRepository.save(message);
    }
}
