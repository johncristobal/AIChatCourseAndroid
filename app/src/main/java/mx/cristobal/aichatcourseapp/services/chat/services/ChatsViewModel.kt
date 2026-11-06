package mx.cristobal.aichatcourseapp.services.chat.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mx.cristobal.aichatcourseapp.services.chat.models.ChatModel

@HiltViewModel
class ChatsViewModel @Inject constructor() : ViewModel() {
    private val _chats = MutableStateFlow<List<ChatModel>>(emptyList())
    val chats: StateFlow<List<ChatModel>> = _chats.asStateFlow()

    init {
        viewModelScope.launch {
//            _chats.value = chatRepository.loadChats()
            _chats.value = ChatModel.mocks
        }
    }
}
