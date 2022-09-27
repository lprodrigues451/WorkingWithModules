package br.com.zup.agenda.ui.registration.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.network.data.model.UserResult
import br.com.zup.agenda.domain.usecase.GetUseCase
import br.com.zup.agenda.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterUserViewModel(application: Application) : AndroidViewModel(application) {

    private val _userResponse = MutableLiveData<UserResult>()
    val userResponse: LiveData<UserResult> = _userResponse
    private val getUseCase = GetUseCase(application)

    val userResultAddState = MutableLiveData<ViewState<UserResult>>()
    fun getUser(user: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getUseCase.execute(user)
                }
                _userResponse.value = response
            } catch (ex: Exception) {
                Log.i("Error", "${ex.message}")
            }
        }

    }

    private fun validaUser(userResult: UserResult): Boolean {
        return if (userResult.name.isNotEmpty()
            && userResult.phone.isNotEmpty()
        ) {
            true
        } else {
            userResultAddState.value =
                ViewState.Error(Throwable("Por favor preencha o campo vazio!"))
            false
        }
    }

    fun insertUser(userResult: UserResult) {
        if (validaUser(userResult)) {
            viewModelScope.launch {
                try {
                    val response = withContext(Dispatchers.IO) {
                        getUseCase.insertUser(userResult)
                    }
                    userResultAddState.value = response
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    userResultAddState.value =
                        ViewState.Error(Throwable("Não foi possível inserir o contato!"))
                }
            }
        }
    }
}