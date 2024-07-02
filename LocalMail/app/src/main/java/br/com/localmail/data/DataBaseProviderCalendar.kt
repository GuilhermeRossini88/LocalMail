package br.com.localmail.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.localmail.dao.CommitmentDao
import br.com.localmail.models.Commitment

@Database(entities = [Commitment::class], version = 1)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun commitmentDao(): CommitmentDao

    companion object {
        @Volatile
        private var INSTANCE: CalendarDatabase? = null

        fun getDatabase(context: Context): CalendarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CalendarDatabase::class.java,
                    "calendar_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
