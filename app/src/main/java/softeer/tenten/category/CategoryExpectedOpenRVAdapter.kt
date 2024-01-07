package softeer.tenten.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import softeer.tenten.R

class CategoryExpectedOpenRVAdapter(
    val context: Context,
    val List: MutableList<CategoryItemModel>
) :
    RecyclerView.Adapter<CategoryExpectedOpenRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_rv_item_expected_open, parent, false)

        return ViewHolder(v)
    }

    // 아이템 클릭시 이벤트 주기 위해 추가한 부분
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (itemClick != null) {
            holder.itemView.setOnClickListener { v ->
                itemClick!!.onClick(v, position)
            }
        }
        holder.bindItems(List[position])
    }

    override fun getItemCount(): Int {
        return List.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: CategoryItemModel) {
            val img = itemView.findViewById<ImageView>(R.id.rvImg)
            val brand = itemView.findViewById<TextView>(R.id.rvBrand)
            val title = itemView.findViewById<TextView>(R.id.rvTitle)
            val date = itemView.findViewById<TextView>(R.id.rvDate)

            brand.text = item.brand
            title.text = item.title
            date.text = item.date
            // 글라이드 라이브러리 사용해 이미지 로드하면 자동 캐싱해서 성능 향상에 도움
            Glide.with(context).load(R.drawable.test1)
                .into(img)
        }
    }
}

