package com.study.Chat.Service;

import com.study.Chat.DTO.ChatRoomDTO;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChatService {

    private Map<String, ChatRoomDTO> chatRoomDTOMap;

    @PostConstruct
    private void init() {
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    // 전체 채팅방 조회
    public List<ChatRoomDTO> findAllRoom() {
        // 채팅방 생성 순서를 최근순으로 반환
        List chatRooms = new ArrayList<>(chatRoomDTOMap.values());
        Collections.reverse(chatRooms);

        return chatRooms;
    }

    // roomID 기준으로 채팅방 찾기
    public ChatRoomDTO findRoomById(String roomId){
        return chatRoomDTOMap.get(roomId);
    }

    // roomName으로 채팅방 만들기
    public ChatRoomDTO createChatRoom(String roomName) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO().create(roomName);

        // map에 채팅룸 아이디와 만들어진 채팅룸을 저장
        chatRoomDTOMap.put(chatRoomDTO.getRoomId(), chatRoomDTO);

        return chatRoomDTO;
    }

    // 채팅방 인원 +1
    public void plusUserCnt(String roomId) {
        ChatRoomDTO room = chatRoomDTOMap.get(roomId);
        room.setUserCount(room.getUserCount()+1);
    }

    // 채팅방 인원 -1
    public void minusUserCnt(String roomId) {
        ChatRoomDTO room = chatRoomDTOMap.get(roomId);
        room.setUserCount(room.getUserCount()-1);
    }

    // 채팅방 유저 리스트에 유저 추가
    public String addUser(String roomId, String userName){
        ChatRoomDTO room = chatRoomDTOMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        // 아이디 중복 확인 후 userList에 추가
        room.getUserlist().put(userUUID, userName);
        return userUUID;
    }

    // 채팅방 유저 이름 중복 확인
    public String isDuplicateName(String roomId, String username){
        ChatRoomDTO room = chatRoomDTOMap.get(roomId);
        String tmp = username;

        // 만약 userName이 중복이라면 랜덤한 숫자를 붙임
        // 이때 랜덤한 숫자를 붙였을 때 getUserlist안에 있는 닉네임이라면 다시 랜덤한 숫자를 붙임
        while(room.getUserlist().containsValue(tmp)){
            int ranNum = (int) (Math.random()*100) + 1;
            tmp = username + ranNum;
        }
        return tmp;
    }

    // 채팅방 유저 리스트 삭제
    public void delUser(String roomId, String userUUID){
        ChatRoomDTO room = chatRoomDTOMap.get(roomId);
        room.getUserlist().remove(userUUID);
    }

    // 채팅방 userName 조회
    public String getUserName(String roomId, String userUUID){
        ChatRoomDTO room = chatRoomDTOMap.get(roomId);
        return room.getUserlist().get(userUUID);
    }

     // 채팅방 전체 userlist 조회
    public ArrayList<String> getUserList(String roomId){
        ArrayList<String> list = new ArrayList<>();

        ChatRoomDTO room = chatRoomDTOMap.get(roomId);

        // hashmap을 for문을 돌린 후
        // value 값만 뽑아내서 list에 저장 후 return
        room.getUserlist().forEach((key, value) -> list.add(value));
        return list;
    }
}
