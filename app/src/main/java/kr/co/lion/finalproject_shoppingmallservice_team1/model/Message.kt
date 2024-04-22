package kr.co.lion.finalproject_shoppingmallservice_team1.model

// 채팅 메시지
data class Message(

    val messageId: String = "", // 메시지 고유 식별자 ID
    val roomId: String = "", // 해당 메시지가 속한 채팅방 고유 식별자 ID

    val senderId: String = "", // 메시지를 보낸 사용자의 고유 식별자 ID
    val content: String = "", // 메시지 내용
    val sentTime: String = "", // 메시지 보낸 시간 (타임스탬프)

    val status: MessageStatus = MessageStatus.SENT // 메시지 상태 (SENT, DELIVERED, READ, FAILED)
){}

// 메시지 상태
enum class MessageStatus(val description: String) {
    SENT("메시지가 성공적으로 전송됨"), // 메시지가 성공적으로 전송됨
    DELIVERED("메시지가 상대방에게 전달됨"), // 메시지가 상대방에게 전달됨
    READ("상대방이 메시지를 읽음"), // 상대방이 메시지를 읽음
    FAILED("메시지 전송 실패") // 메시지 전송 실패
}