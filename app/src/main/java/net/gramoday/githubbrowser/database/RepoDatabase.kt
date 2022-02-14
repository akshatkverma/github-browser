package net.gramoday.githubbrowser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.gramoday.githubbrowser.dao.RepoDao
import net.gramoday.githubbrowser.entities.Repo


@Database(entities = [Repo::class],version=1, exportSchema = false)
abstract class RepoDatabase :RoomDatabase(){

    abstract fun getDao():RepoDao

    companion object{

        @Volatile
        private var INSTANCE: RepoDatabase? = null

        fun getDatabase(context: Context): RepoDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RepoDatabase::class.java,
                    "repo_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}