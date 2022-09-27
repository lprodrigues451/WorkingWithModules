package br.com.zup.agenda.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.zup.agenda.data.datasource.local.dao.UserDAO
import com.example.network.data.model.UserResult


@Database(entities = [UserResult::class], version = 1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDAO():UserDAO
    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getUserDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "tableUser"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}