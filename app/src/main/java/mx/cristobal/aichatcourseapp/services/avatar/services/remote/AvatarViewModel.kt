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

    init {
        loadRecent()
    }

    fun getAvatar(avatarId: String): AvatarModel {
        return AvatarModel.mocks.first { it.avatarId == avatarId }
    }

    fun loadRecent() {
        _avatars.value = AvatarModel.mocks
    }
}