package br.com.localmail.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val section: Int,
    val name: String,
    val subject: String,
    val creationTime: Long
)
