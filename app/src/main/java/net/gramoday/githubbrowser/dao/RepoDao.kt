package net.gramoday.githubbrowser.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.gramoday.githubbrowser.entities.Repo


@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertRepo(repoItem: Repo)

    @Delete
    suspend fun deleteRepo(repoItem: Repo)

    @Query("SELECT * FROM Repo ORDER BY pid ASC")
    fun getClassItems(): LiveData<List<Repo>>
}