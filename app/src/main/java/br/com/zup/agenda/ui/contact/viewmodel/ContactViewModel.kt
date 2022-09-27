package br.com.zup.agenda.ui.contact.viewmodel

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

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val getUseCase = GetUseCase(application)
    val useListState = MutableLiveData<ViewState<List<UserResult>>>()

    fun getAllUser() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    getUseCase.getAllUser()
                }
                useListState.value = response
            } catch (ex: Exception) {
                useListState.value =
                    ViewState.Error(Throwable("Não foi possível carregar a lista!"))
            }
        }
    }
}