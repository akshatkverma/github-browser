package net.gramoday.githubbrowser.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.gramoday.githubbrowser.R
import net.gramoday.githubbrowser.entities.Repo

class BranchesAdapter(
    private val context: Context
): RecyclerView.Adapter<BranchesAdapter.BranchesViewHolder> (){

    private var dataset: MutableList<String> = ArrayList()

    var onItemClick: ((String, Int) -> Unit)? = null

    inner class BranchesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val branchName: TextView =view.findViewById(R.id.branchName)
        init {
            itemView.setOnClickListener{
                onItemClick?.invoke(dataset[adapterPosition],adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BranchesAdapter.BranchesViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.branches_item, parent, false)

        return BranchesViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: BranchesAdapter.BranchesViewHolder, position: Int) {
        holder.branchName.text=dataset[position]
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateBranchItems(updatedDataset:List<String>)
    {
        dataset.clear()
        dataset.addAll(updatedDataset)
        notifyDataSetChanged()
    }

}