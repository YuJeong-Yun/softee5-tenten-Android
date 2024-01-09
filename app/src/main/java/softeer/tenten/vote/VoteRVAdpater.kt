package softeer.tenten.vote

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.R

class VoteRVAdapter(val context: Context, val items: MutableList<VoteItemModel>) :
    RecyclerView.Adapter<VoteRVAdapter.ViewHolder>() {
    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteRVAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.vote_list_rv_item, parent, false)

        return ViewHolder(v, mListener)
    }

    override fun onBindViewHolder(holder: VoteRVAdapter.ViewHolder, position: Int) {
        val item = items[position]

        holder.location.text = item.location
        holder.checked = item.checked

        if(holder.checked!!){
            holder.voteAble.visibility = View.VISIBLE
            holder.voteDisable.visibility = View.INVISIBLE
            holder.able()
        } else {
            holder.voteAble.visibility = View.INVISIBLE
            holder.voteDisable.visibility = View.VISIBLE
            holder.disable()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View, listener: VoteRVAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val location = itemView.findViewById<TextView>(R.id.rvVoteLocation)
        val voteAble = itemView.findViewById<ImageView>(R.id.rvVoteAble)
        val voteDisable = itemView.findViewById<ImageView>(R.id.rvVoteDisable)
        var checked: Boolean? = null

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        fun able(){
            itemView.setBackgroundResource(R.drawable.dialog_select_round)
        }

        fun disable(){
            itemView.setBackgroundResource(R.drawable.dialog_unselect_round)
        }
    }
}
