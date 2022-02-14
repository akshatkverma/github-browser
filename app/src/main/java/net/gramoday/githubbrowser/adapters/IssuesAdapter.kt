package net.gramoday.githubbrowser.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import net.gramoday.githubbrowser.R
import net.gramoday.githubbrowser.entities.Issues
import com.bumptech.glide.request.target.Target

class IssuesAdapter(
    context: Context
) : RecyclerView.Adapter<IssuesAdapter.IssuesViewHolder>() {

    private var dataset: MutableList<Issues> = ArrayList()

    var onItemClick: ((Issues, Int) -> Unit)? = null

    inner class IssuesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.titleIssue)
        val avatar: ImageView = view.findViewById(R.id.userAvatar)
        val name: TextView = view.findViewById(R.id.userName)
        val pg:ProgressBar=view.findViewById(R.id.pgBar)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(dataset[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.issues_item, parent, false)

        return IssuesViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: IssuesViewHolder, position: Int) {
        holder.name.text=dataset[position].name
        holder.title.text=dataset[position].title
        Glide.with(holder.itemView.context).load(dataset[position].url).listener(object:
            RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                holder.pg.visibility = View.GONE
                holder.avatar.visibility=View.VISIBLE
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                holder.pg.visibility = View.GONE
                holder.avatar.visibility=View.VISIBLE
                return false
            }
        }).into(holder.avatar)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateIssuesItems(updatedDataset:List<Issues>)
    {
        dataset.clear()
        dataset.addAll(updatedDataset)
        notifyDataSetChanged()
    }
}