package net.gramoday.githubbrowser.adapters

import android.annotation.SuppressLint
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

class CommitsAdapter(
    _context:Context
) :RecyclerView.Adapter<CommitsAdapter.CommitsViewHolder>(){
    private val context=_context
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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CommitsViewHolder, position: Int) {
        holder.name.text=dataset[position].userName
        holder.msg.text="Commit Message : \n${dataset[position].msg}"
        holder.date.text=formatDate(dataset[position].date)
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

    private fun formatDate(og: String): String {
        return "${og[8]}${og[9]} ${getMonth("${og[5]}${og[6]}")} ${og[2]}${og[3]}"
    }

    private fun getMonth(mon:String): String {
        return when(mon)
        {
            "01"->"Jan"
            "02"->"Feb"
            "03"->"Mar"
            "04"->"Apr"
            "05"->"May"
            "06"->"June"
            "07"->"July"
            "08"->"Aug"
            "09"->"Sept"
            "10"->"Oct"
            "11"->"Nov"
            "12"->"Dec"
            else -> {"Jan"}
        }
    }
}