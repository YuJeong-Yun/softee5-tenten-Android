package softeer.tenten.event

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import softeer.tenten.R
import softeer.tenten.vote.VoteRVAdapter

class EventRVAdapter(val context: Context, val items: MutableList<EventItemModel>) :
    RecyclerView.Adapter<EventRVAdapter.ViewHolder>()  {

    private lateinit var eventListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        eventListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventRVAdapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.event_rv_item, parent, false)

        return ViewHolder(v, eventListener)
    }

    override fun onBindViewHolder(holder: EventRVAdapter.ViewHolder, position: Int) {
        val item = items[position]

        // 글라이드 라이브러리 사용해 이미지 로드하면 자동 캐싱해서 성능 향상에 도움
        Glide.with(context)
            .load(item.img)
            .into(holder.img)

        holder.title.text = item.title
        holder.description.text = item.description
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View, listener: EventRVAdapter.onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.rvEventImage)
        val title = itemView.findViewById<TextView>(R.id.rvEventTitle)
        val description = itemView.findViewById<TextView>(R.id.rvEventDescription)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}