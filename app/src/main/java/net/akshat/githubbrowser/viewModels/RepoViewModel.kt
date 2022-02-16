package net.akshat.githubbrowser.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.akshat.githubbrowser.database.RepoDatabase
import net.akshat.githubbrowser.entities.Repo
import net.akshat.githubbrowser.repository.RepoRepository

class RepoViewModel(application: Application) : AndroidViewModel(application) {

    val allRepoItems: LiveData<List<Repo>>

    private val repository: RepoRepository

    init {
        val dao = RepoDatabase.getDatabase(application).getDao()
        repository = RepoRepository(dao)
        allRepoItems = repository.allRepos
    }

    fun insertRepo(repoItem: Repo) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertRepo(repoItem)
    }

    fun deleteRepo(repoItem: Repo) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRepo(repoItem)
    }
}