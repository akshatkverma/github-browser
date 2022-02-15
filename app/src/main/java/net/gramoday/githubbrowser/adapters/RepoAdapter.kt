package net.gramoday.githubbrowser.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import net.gramoday.githubbrowser.R
import net.gramoday.githubbrowser.entities.Repo

class RepoAdapter(
    private val context: Context,
    private val dataset: MutableList<Repo>
) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder> (){

    var onItemClick: ((Repo, Int) -> Unit)? = null
    var onItemLongClick: ((Repo,Int) -> Unit)?= null

    inner class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName: TextView =view.findViewById(R.id.repoName)
        val desc:TextView=view.findViewById(R.id.description)
        val button:ImageButton=view.findViewById(R.id.sentImage)
        init {
            itemView.setOnClickListener{
                onItemClick?.invoke(dataset[adapterPosition],adapterPosition)
            }
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(dataset[adapterPosition],adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.repo_item, parent, false)

        return RepoViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.repoName.text = dataset[position].owner+"/"+dataset[position].repoName
        holder.desc.text = dataset[position].description

        holder.button.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Have a look at this awesome repository.\n" +
                    "Organization : " + dataset[position].owner+"\n"+
                    "Repository : " + dataset[position].repoName+"\n"+
                    "Description : " + dataset[position].description+"\n"+
                    "Link : " + "https://github.com/"+dataset[position].owner+"/"+dataset[position].repoName
                    )
            shareIntent.type = "text/plain"
            context.startActivity(Intent.createChooser(shareIntent,"Share with.."))
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateClassItems(updatedDataset:List<Repo>)
    {
        dataset.clear()
        dataset.addAll(updatedDataset)
        notifyDataSetChanged()
    }
}