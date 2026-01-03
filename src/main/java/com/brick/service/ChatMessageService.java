package com.brick.service;

import com.brick.dto.ChatMessageResponse;

import java.util.List;

public interface ChatMessageService {
    ChatMessageResponse send(Long roomId, Long senderId, String content);
    List<ChatMessageResponse> getMessages(Long roomId, Long requestId);


}
