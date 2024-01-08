package softeer.tenten.vote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.R

class VoteResultRVAdapter(val context: Context, val items: MutableList<VoteResultItemModel>) :
    RecyclerView.Adapter<VoteResultRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VoteResultRVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.vote_result_rv_item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: VoteResultRVAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.location.text = item.location
        holder.rate.text = item.rate.toString() + "%"
        holder.progressBar.setProgress(item.rate)
        holder.checked = item.checked

        if(item.checked){
            holder.checkedImage.visibility = View.VISIBLE
        } else{
            holder.checkedImage.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val location = itemView.findViewById<TextView>(R.id.rvVoteResultLocation)
        val rate = itemView.findViewById<TextView>(R.id.rvVoteResultRate)
        val progressBar = itemView.findViewById<ProgressBar>(R.id.rvVoteResultProgressBar)
        val checkedImage = itemView.findViewById<ImageView>(R.id.rvVoteResultAble)
        var checked: Boolean? = null
    }
}