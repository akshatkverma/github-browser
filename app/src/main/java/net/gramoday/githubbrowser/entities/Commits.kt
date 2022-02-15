package net.gramoday.githubbrowser.entities

data class Commits(
    var sha:String,
    var date:String,
    var msg:String,
    var userName:String,
    var imgUrl:String
)