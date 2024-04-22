package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 채팅방
data class ChatRoom(

    val roomId: String = "", // 채팅방 고유 식별자 (ID)

    val partnerId: String = "", // 상대방의 고유 식별자 (ID) - 닉네임, 프로필 사진

    var lastMessage: String = "", // 마지막 채팅 기록
    var lastMessageTime: String = "", // 마지막 채팅 시간

    var unreadCount: Int = 0, // 읽지 않은 메시지 수

    var isMuted: Boolean = false, // 채팅방 알림을 끈 상태 여부

    var status: String = ""  // 채팅방 상태
)