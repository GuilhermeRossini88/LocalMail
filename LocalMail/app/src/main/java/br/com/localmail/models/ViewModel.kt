package br.com.localmail.models

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.localmail.data.CalendarDatabase
import kotlinx.coroutines.launch

class SuperCalendarViewModel(private val context: Context) : ViewModel() {
    private val database = CalendarDatabase.getDatabase(context)

    fun getCommitmentsByDate(date: String) = database.commitmentDao().getCommitmentsByDate(date)

    fun addCommitment(commitment: Commitment) {
        viewModelScope.launch {
            database.commitmentDao().insertCommitment(commitment)
        }
    }
}