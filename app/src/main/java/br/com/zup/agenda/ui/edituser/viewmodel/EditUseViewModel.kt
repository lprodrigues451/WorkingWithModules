package br.com.zup.agenda.ui.edituser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.network.data.model.UserResult
import br.com.zup.agenda.domain.usecase.GetUseCase
import br.com.zup.agenda.viewstate.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditUseViewModel(application: Application) : AndroidViewModel(application) {
    private val getUseCase = GetUseCase(application)
    val userResultAddState = MutableLiveData<ViewState<UserResult>>()

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

    fun deleteAllList(userResult: UserResult) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getUseCase.deleteAllList(userResult)
            }
        }
    }

    fun updateItem(userResult: UserResult) {
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
                        ViewState.Error(Throwable("Não foi possível atualizar o contato!"))
                }
            }
        }
    }
}