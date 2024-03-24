package com.study.Chat.DTO;

import lombok.Data;

import java.util.HashMap;
import java.util.UUID;

// Stomp를 통해 pub/sub를 사용하면 구독자 관리가 알아서 된다.
// 따라서 따로 세션 관리를 하는 코드를 작성할 필요가 없고,
// 메시지를 다른 세션의 클라이언트에게 발송하는 것도 구현할 필요가 없다.
@Data
public class ChatRoomDTO {
    private String roomId; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private long userCount; // 채팅방 인원 수

    private HashMap<String, String> userlist = new HashMap<String, String>();

    public ChatRoomDTO create(String roomName) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.roomId = UUID.randomUUID().toString();
        chatRoomDTO.roomName = roomName;

        return chatRoomDTO;
    }
}
