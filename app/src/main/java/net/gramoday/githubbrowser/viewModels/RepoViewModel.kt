package net.gramoday.githubbrowser.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.gramoday.githubbrowser.database.RepoDatabase
import net.gramoday.githubbrowser.entities.Repo
import net.gramoday.githubbrowser.repository.RepoRepository

class RepoViewModel(application: Application) : AndroidViewModel(application) {

    val allRepoItems: LiveData<List<Repo>>
    var currentPos:Int
    private val repository: RepoRepository
    init {
        val dao = RepoDatabase.getDatabase(application).getDao()
        repository = RepoRepository(dao)
        allRepoItems = repository.allRepos
        currentPos=0
    }

    fun insertRepo(repoItem:Repo)=viewModelScope.launch(Dispatchers.IO) {
        repository.insertRepo(repoItem)
    }

    fun deleteRepo(repoItem:Repo)=viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRepo(repoItem)
    }
}