package net.akshat.githubbrowser.repository

import androidx.lifecycle.LiveData
import net.akshat.githubbrowser.dao.RepoDao
import net.akshat.githubbrowser.entities.Repo

class RepoRepository(private val dao: RepoDao) {

    val allRepos: LiveData<List<Repo>> = dao.getClassItems()

    suspend fun insertRepo(repoItem: Repo) = dao.insertRepo(repoItem)

    suspend fun deleteRepo(repoItem: Repo) = dao.deleteRepo(repoItem)
}