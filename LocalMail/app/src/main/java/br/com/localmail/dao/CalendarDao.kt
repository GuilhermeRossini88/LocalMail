package br.com.localmail.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.localmail.models.Commitment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommitmentDao {
    @Insert
    suspend fun insertCommitment(commitment: Commitment)

    @Query("SELECT * FROM commitments WHERE date = :date ORDER BY time ASC")
    fun getCommitmentsByDate(date: String): Flow<List<Commitment>>
}
