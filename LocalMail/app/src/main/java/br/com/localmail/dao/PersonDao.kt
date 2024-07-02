package br.com.localmail.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.localmail.models.Person

@Dao
interface PersonDao {
    @Insert
    suspend fun insert(person: Person)

    @Delete
    suspend fun delete(person: Person)

    @Query("SELECT * FROM persons")
    public fun getAllPersons(): List<Person>
}
