package net.gramoday.githubbrowser.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repo(
    val owner:String,
    val repoName:String,
    val description:String
){
    @PrimaryKey(autoGenerate = true) var pid:Int=0
}