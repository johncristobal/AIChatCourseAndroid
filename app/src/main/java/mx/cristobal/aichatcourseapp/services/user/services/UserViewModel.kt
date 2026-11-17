package mx.cristobal.aichatcourseapp.services.user.services

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import mx.cristobal.aichatcourseapp.services.user.models.UserModel
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val billingRepository: BillingRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val firebaseUser: StateFlow<FirebaseUser?> = authRepository.currentUser

    val currentUser: StateFlow<UserModel?> = authRepository.currentUser
        .map { firebaseUser ->
            if (firebaseUser != null) {
                UserModel(
                    userId = firebaseUser.uid,
                    dateCreated = Date(firebaseUser.metadata?.creationTimestamp ?: System.currentTimeMillis()),
                    didCompleteOnboarding = true,
                    profileColorHex = "#4285F4"
                )
            } else {
                null
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    val productDetails: StateFlow<List<ProductDetails>> = billingRepository.productDetails
    val purchases: StateFlow<List<Purchase>> = billingRepository.purchases

    private val _signInError = MutableStateFlow<String?>(null)
    val signInError: StateFlow<String?> = _signInError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        billingRepository.startConnection()
    }

    fun signInWithGoogle(context: Context, onSuccess: (() -> Unit)? = null, onError: ((String) -> Unit)? = null) {
        viewModelScope.launch {
            _isLoading.value = true
            _signInError.value = null
            val result = authRepository.signInWithGoogle(context)
            _isLoading.value = false
            result.onSuccess {
                onSuccess?.invoke()
            }.onFailure { error ->
                val errorMessage = error.localizedMessage ?: "Sign in failed"
                _signInError.value = errorMessage
                onError?.invoke(errorMessage)
            }
        }
    }

    fun signOut() {
        authRepository.signOut()
    }

    fun buyProduct(activity: Activity, productDetails: ProductDetails) {
        billingRepository.launchBillingFlow(activity, productDetails)
    }

    override fun onCleared() {
        super.onCleared()
        billingRepository.endConnection()
    }
}
