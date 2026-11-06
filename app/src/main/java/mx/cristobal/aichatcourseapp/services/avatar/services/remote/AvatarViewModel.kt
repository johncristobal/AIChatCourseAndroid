package mx.cristobal.aichatcourseapp.services.avatar.services.remote

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import mx.cristobal.aichatcourseapp.services.avatar.models.AvatarModel
import javax.inject.Inject

@HiltViewModel
class AvatarViewModel @Inject constructor() : ViewModel() {

    private val _avatars = MutableStateFlow<List<AvatarModel>>(emptyList())
    val avatars: StateFlow<List<AvatarModel>> = _avatars.asStateFlow()

    private val _avatarsForAuthor = MutableStateFlow<List<AvatarModel>>(emptyList())
    val avatarsForAuthor: StateFlow<List<AvatarModel>> = _avatarsForAuthor.asStateFlow()

    init {
        getPopularAvatars()
    }

    fun getAvatar(avatarId: String): AvatarModel {
        return AvatarModel.mocks.first { it.avatarId == avatarId }
    }

    fun getPopularAvatars() {
        _avatars.value = AvatarModel.mocks
    }

    fun getAvatarsForAuthor(authorId: String) {
        _avatarsForAuthor.value = AvatarModel.mocks
    }
}