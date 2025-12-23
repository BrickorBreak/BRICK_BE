package com.brick.repository;

import com.brick.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
// 방과 유저 연결을 한줄로 저장하는 엔티티
// id room_id user_id
// 1    101     3
// 3    102     3 -> 3번 유저는 101,102번 참가중
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    // 101번방에 userid = 3인 사람 들어가 있는지 확인
    boolean existsByRoom_RoomIdAndUser_UserId(Long roomId, Long userId);

    // userId가 참가한 member리스트  -> userId가 3이면 전체
    List<ChatRoomMember> findByUser_UserId(Long userId);

    @Query("""
        select crm.room.roomId
        from ChatRoomMember crm
        where crm.user.userId = :userId
        order by crm.room.roomId desc
    """)
    // 유저 id에 방 번호 조회
    List<Long> findRoomIdsByUserId(Long userId);
}
