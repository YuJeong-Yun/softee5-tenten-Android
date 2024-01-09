package softeer.tenten.reivew

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import softeer.tenten.R

class ReviewRVAdapter (val context: Context, val items: MutableList<ReviewItemModel>) :
    RecyclerView.Adapter<ReviewRVAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewRVAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.review_rv_item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReviewRVAdapter.ViewHolder, position: Int) {
        val item = items[position]

        // 글라이드 라이브러리 사용해 이미지 로드하면 자동 캐싱해서 성능 향상에 도움
        Glide.with(context)
            .load(item.img)
            .override(200, 200)
            .into(holder.img)

        holder.title.text = item.title
        holder.location.text = item.location
        holder.date.text = item.date
        holder.content.text = item.content
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.rvReviewImg)
        val title = itemView.findViewById<TextView>(R.id.rvReviewNickname)
        val location = itemView.findViewById<TextView>(R.id.rvReviewLocation)
        val date = itemView.findViewById<TextView>(R.id.rvReviewDate)
        val content = itemView.findViewById<TextView>(R.id.rvReviewContent)
    }
}