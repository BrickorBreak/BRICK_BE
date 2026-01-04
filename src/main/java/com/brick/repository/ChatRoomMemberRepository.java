package com.brick.repository;

import com.brick.entity.ChatRoomMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {

    boolean existsByRoom_RoomIdAndUser_UserId(Long roomId, Long userId);

    List<ChatRoomMember> findByUser_UserId(Long userId);

    @Query("""
        select crm.room.roomId
        from ChatRoomMember crm
        where crm.user.userId = :userId
        order by crm.room.roomId desc
    """)
    List<Long> findRoomIdsByUserId(@Param("userId") Long userId);

    List<ChatRoomMember> findByRoom_RoomId(Long roomId);

    /**
     * 1:1 채팅방이 이미 존재하는지 찾기
     * - 두 유저가 모두 포함된 roomId를 찾는다.
     * - (이미 중복방이 존재할 수도 있어서) List로 받는다.
     */
    @Query("""
        select crm.room.roomId
        from ChatRoomMember crm
        where crm.user.userId in (:userA, :userB)
        group by crm.room.roomId
        having count(distinct crm.user.userId) = 2
        order by crm.room.roomId asc
    """)
    List<Long> findPrivateRoomIdsBetween(@Param("userA") Long userA,
                                         @Param("userB") Long userB);
}
