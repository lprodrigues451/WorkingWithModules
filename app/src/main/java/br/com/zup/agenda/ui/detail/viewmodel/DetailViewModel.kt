package br.com.zup.agenda.ui.detail.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.data.model.UserResult
import br.com.zup.agenda.domain.usecase.GetUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val getUseCase = GetUseCase(application)

    fun deleteAllList(userResult: UserResult) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getUseCase.deleteAllList(userResult)
            }
        }
    }

}