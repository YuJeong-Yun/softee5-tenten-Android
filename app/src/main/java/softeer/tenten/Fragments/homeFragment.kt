package softeer.tenten.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.Category.CategoryItemModel
import softeer.tenten.Category.CategoryRVAdapter
//import softeer.tenten.Category.CategoryItemModel
import softeer.tenten.R


class homeFragment : Fragment() {

    private val items = mutableListOf<CategoryItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //////////////////// RVAdapter에 전달할 데이터 담기 ////////////////////
        items.add(CategoryItemModel("test1.png", "스타벅스", "타이틀", "장소", "날짜"))
        items.add(CategoryItemModel("test1.png", "스타벅스2", "타이틀", "장소", "날짜"))
        items.add(CategoryItemModel("test1.png", "스타벅스3", "타이틀", "장소", "날짜"))
        items.add(CategoryItemModel("test1.png", "스타벅스4", "타이틀", "장소", "날짜"))

        val recyclerview = view.findViewById<RecyclerView>(R.id.categoryRV)
        val rvAdapter = CategoryRVAdapter(requireContext(), items)
        recyclerview.adapter = rvAdapter

        //////////////////////////// 웹 뷰 //////////////////////////////
//        아이템 클릭시 새로운 액티비티로 (웹뷰) 이동 이벤트 주기 위해 추가한 부분
        rvAdapter.itemClick =
            object : CategoryRVAdapter.ItemClick {
                override fun onClick(view: View, position: Int) {
//                    // 선택한 아이템의 정보 담아서 액티비티 이동. 해당 액티비티에서 웹 뷰 띄워줌!
//                    val intent = Intent(baseContext, ViewActivity::class.java)
//                    intent.putExtra("url", items[position].url)
//                    intent.putExtra("title", items[position].titleText)
//                    intent.putExtra("imageUrl", items[position].imageUrl)
//
//                    startActivity(intent)
                }
            }

        // 한 줄에 항목 2개씩 나옴
        recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)

        return view
    }
}