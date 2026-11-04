package mx.cristobal.aichatcourseapp.services.chat.models

import java.util.Calendar
import java.util.Date

data class ChatModel(
    val id: String,
    val userId: String,
    val avatarId: String,
    val dateCreated: Date,
    val dataModified: Date
) {
    companion object {

        fun chatId(userId: String, avatarId: String): String {
            return "${userId}_${avatarId}"
        }

        fun new(userId: String, avatarId: String) = ChatModel(
            id = chatId(userId, avatarId),
            userId = userId,
            avatarId = avatarId,
            dateCreated = Date(),
            dataModified = Date()
        )

        val mocks = listOf(
            ChatModel(
                id = "mock_chat_1",
                userId = "user_1",
                avatarId = "avatar_1",
                dateCreated = Date(),
                dataModified = Date()
            ),
            ChatModel(
                id = "mock_chat_2",
                userId = "user_1",
                avatarId = "avatar_2",
                dateCreated = Calendar.getInstance().time,
                dataModified = Date()
            ),
            ChatModel(
                id = chatId("user_1", "avatar_3"),
                userId = "user_1",
                avatarId = "avatar_3",
                dateCreated = Date(),
                dataModified = Date()
            ),
            ChatModel(
                id = chatId("user_1", "avatar_4"),
                userId = "user_1",
                avatarId = "avatar_4",
                dateCreated = Date(),
                dataModified = Date()
            ),
            ChatModel(
                id = chatId("user_1", "avatar_5"),
                userId = "user_1",
                avatarId = "avatar_5",
                dateCreated = Date(),
                dataModified = Date()
            )
        )

        val mock = mocks.first()
    }
}
