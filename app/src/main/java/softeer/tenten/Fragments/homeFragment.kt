package softeer.tenten.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.Category.CategoryExpectedOpenRVAdapter
import softeer.tenten.Category.CategoryItemModel
import softeer.tenten.Category.CategoryRVAdapter
import softeer.tenten.Category.CategoryRecommendCarRVAdapter
import softeer.tenten.R


class homeFragment : Fragment() {

    private val nearCarItems = mutableListOf<CategoryItemModel>()
    private val recommendCarItems = mutableListOf<CategoryItemModel>()
    private val expectedOpen = mutableListOf<CategoryItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //////////////////// RVAdapter에 전달할 데이터 담기 ////////////////////
        // 내 근처 팝업카
        nearCarItems.add(CategoryItemModel("test1.png", "스타벅스", "타이틀", "장소", "날짜"))
        nearCarItems.add(CategoryItemModel("test1.png", "스타벅스2", "타이틀", "장소", "날짜"))
        nearCarItems.add(CategoryItemModel("test1.png", "스타벅스3", "타이틀", "장소", "날짜"))
        nearCarItems.add(CategoryItemModel("test1.png", "스타벅스4", "타이틀", "장소", "날짜"))

        // 추천하는 팝업카
        recommendCarItems.add(CategoryItemModel("test1.png", "스타벅스", "타이틀", "장소", "날짜"))
        recommendCarItems.add(CategoryItemModel("test1.png", "스타벅스2", "타이틀", "장소", "날짜"))
        recommendCarItems.add(CategoryItemModel("test1.png", "스타벅스3", "타이틀", "장소", "날짜"))
        recommendCarItems.add(CategoryItemModel("test1.png", "스타벅스3", "타이틀", "장소", "날짜"))

        // 오픈 예정
        expectedOpen.add(CategoryItemModel("test1.png", "스타벅스", "타이틀", "장소", "날짜"))
        expectedOpen.add(CategoryItemModel("test1.png", "스타벅스2", "타이틀", "장소", "날짜"))
        expectedOpen.add(CategoryItemModel("test1.png", "스타벅스3", "타이틀", "장소", "날짜"))
        expectedOpen.add(CategoryItemModel("test1.png", "스타벅스3", "타이틀", "장소", "날짜"))

        val rvNearCar = view.findViewById<RecyclerView>(R.id.categoryNearCar)
        rvNearCar.adapter = CategoryRVAdapter(requireContext(), nearCarItems)

        val rvRecommendCar = view.findViewById<RecyclerView>(R.id.categoryRecommendCar)
        rvRecommendCar.adapter = CategoryRecommendCarRVAdapter(requireContext(), recommendCarItems)


        val rvExpectedOpen = view.findViewById<RecyclerView>(R.id.categoryExpectedOpen)
        rvExpectedOpen.adapter = CategoryExpectedOpenRVAdapter(requireContext(), expectedOpen)

        //////////////////////////// 웹 뷰 //////////////////////////////
//        아이템 클릭시 새로운 액티비티로 (웹뷰) 이동 이벤트 주기 위해 추가한 부분
//        rvAdapter.itemClick = object : CategoryRVAdapter.ItemClick {
//            override fun onClick(view: View, position: Int) {
//                    // 선택한 아이템의 정보 담아서 액티비티 이동. 해당 액티비티에서 웹 뷰 띄워줌!
//                    val intent = Intent(baseContext, ViewActivity::class.java)
//                    intent.putExtra("url", nearCarItems[position].url)
//                    intent.putExtra("title", nearCarItems[position].titleText)
//                    intent.putExtra("imageUrl", nearCarItems[position].imageUrl)
//
//                    startActivity(intent)
//            }
//        }


        rvNearCar.layoutManager = GridLayoutManager(requireContext(), 2)
        // 수평 정렬
        rvRecommendCar.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rvExpectedOpen.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        return view
    }
}