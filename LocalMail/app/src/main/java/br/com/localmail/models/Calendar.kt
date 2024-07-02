package br.com.localmail.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commitments")
data class Commitment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val time: String,
    val text: String
)
