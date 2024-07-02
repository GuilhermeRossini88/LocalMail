package br.com.localmail.data

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.localmail.dao.PersonDao
import br.com.localmail.models.Commitment
import br.com.localmail.models.Person

@Database(entities = [Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}
