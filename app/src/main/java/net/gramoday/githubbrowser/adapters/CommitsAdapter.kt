package net.gramoday.githubbrowser.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import net.gramoday.githubbrowser.R
import net.gramoday.githubbrowser.entities.Commits
import org.w3c.dom.Text

class CommitsAdapter(
    context:Context
) :RecyclerView.Adapter<CommitsAdapter.CommitsViewHolder>(){

    private var dataset= mutableListOf<Commits>()

    inner class CommitsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView =view.findViewById(R.id.date)
        val sha:TextView=view.findViewById(R.id.commitId)
        val msg:TextView=view.findViewById(R.id.commitMessage)
        val name:TextView=view.findViewById(R.id.userName)
        val avatar:ImageView=view.findViewById(R.id.userAvatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommitsViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.commit_item, parent, false)

        return CommitsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CommitsViewHolder, position: Int) {
        holder.name.text=dataset[position].userName
        holder.msg.text=dataset[position].msg
        holder.date.text=dataset[position].date
        holder.sha.text=dataset[position].sha.substring(0,7)
        Glide.with(holder.itemView.context).load(dataset[position].imgUrl).listener(object:
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }
        }).into(holder.avatar)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateCommits(updatedDataset:List<Commits>)
    {
        dataset.clear()
        dataset.addAll(updatedDataset)
        notifyDataSetChanged()
    }
}