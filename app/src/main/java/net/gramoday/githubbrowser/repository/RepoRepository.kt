package net.gramoday.githubbrowser.repository

import androidx.lifecycle.LiveData
import net.gramoday.githubbrowser.dao.RepoDao
import net.gramoday.githubbrowser.entities.Repo

class RepoRepository (private val dao:RepoDao){

    val allRepos: LiveData<List<Repo>> = dao.getClassItems()

    suspend fun insertRepo(repoItem: Repo)=dao.insertRepo(repoItem)

    suspend fun deleteRepo(repoItem:Repo)=dao.deleteRepo(repoItem)
}