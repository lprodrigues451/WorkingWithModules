package br.com.zup.agenda.domain.repository

import br.com.zup.agenda.data.datasource.local.dao.UserDAO
import com.example.network.data.datasource.remote.RetrofitCep
import com.example.network.data.model.UserResult

class UserRepository(private val userDAO: UserDAO) {
    suspend fun getUser(user: String): UserResult {
        return RetrofitCep.api.getAddress(user)

    }

    fun insertUser(userResult: UserResult) {
        userDAO.insertUser(userResult)
    }

    fun deleteUser(userResult: UserResult) {
        userDAO.deleteUser(userResult)
    }

    fun getAllUser(): List<UserResult> = userDAO.getUser()

    fun updateUser(userResult: UserResult) {
        userDAO.update(userResult)
    }
}
