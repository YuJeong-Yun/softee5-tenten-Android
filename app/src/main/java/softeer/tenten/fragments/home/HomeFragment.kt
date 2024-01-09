package softeer.tenten.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import softeer.tenten.ExpectedOpenActivity
import softeer.tenten.category.CategoryExpectedOpenRVAdapter
import softeer.tenten.category.CategoryItemModel
import softeer.tenten.category.CategoryNearCarRVAdapter
import softeer.tenten.category.CategoryRecommendCarRVAdapter
import softeer.tenten.MoreInfoActivity
import softeer.tenten.R

class HomeFragment : Fragment() {

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
        nearCarItems.add(CategoryItemModel(1,"test1.png", "스타벅스", "타이틀", "장소", "날짜"))
        nearCarItems.add(CategoryItemModel(2,"test1.png", "스타벅스2", "타이틀", "장소", "날짜"))
        nearCarItems.add(CategoryItemModel(3,"test1.png", "스타벅스3", "타이틀", "장소", "날짜"))
        nearCarItems.add(CategoryItemModel(4,"test1.png", "스타벅스4", "타이틀", "장소", "날짜"))

        // 추천하는 팝업카
        recommendCarItems.add(CategoryItemModel(1,"test1.png", "스타벅스", "타이틀", "장소", "날짜"))
        recommendCarItems.add(CategoryItemModel(2,"test1.png", "스타벅스2", "타이틀", "장소", "날짜"))
        recommendCarItems.add(CategoryItemModel(3,"test1.png", "스타벅스3", "타이틀", "장소", "날짜"))
        recommendCarItems.add(CategoryItemModel(4,"test1.png", "스타벅스3", "타이틀", "장소", "날짜"))

        // 오픈 예정
        expectedOpen.add(CategoryItemModel(5,"test1.png", "스타벅스", "타이틀", "장소", "날짜"))
        expectedOpen.add(CategoryItemModel(6,"test1.png", "스타벅스2", "타이틀", "장소", "날짜"))
        expectedOpen.add(CategoryItemModel(7,"test1.png", "스타벅스3", "타이틀", "장소", "날짜"))
        expectedOpen.add(CategoryItemModel(8,"test1.png", "스타벅스3", "타이틀", "장소", "날짜"))

        val rvNearCar = view.findViewById<RecyclerView>(R.id.categoryNearCar)
        val rvNearCarAdapter = CategoryNearCarRVAdapter(requireContext(), nearCarItems)
        rvNearCar.adapter = rvNearCarAdapter

        val rvRecommendCar = view.findViewById<RecyclerView>(R.id.categoryRecommendCar)
        rvRecommendCar.adapter = CategoryRecommendCarRVAdapter(requireContext(), recommendCarItems)


        val rvExpectedOpen = view.findViewById<RecyclerView>(R.id.categoryExpectedOpen)
        val rvExpectedOpenAdapter = CategoryExpectedOpenRVAdapter(requireContext(), expectedOpen)
        rvExpectedOpen.adapter = rvExpectedOpenAdapter

        // 아이템 클릭시 상세 정보로 이동
        rvNearCarAdapter.itemClick = object : CategoryNearCarRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                // 선택한 팝업스토어의 정보 담아서 액티비티 이동
                val intent = Intent(requireContext(), MoreInfoActivity::class.java)
                intent.putExtra("id", nearCarItems[position].id)
                startActivity(intent)
            }
        }

        rvExpectedOpenAdapter.itemClick = object : CategoryExpectedOpenRVAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                // 선택한 팝업스토어의 정보 담아서 액티비티 이동
                val intent = Intent(requireContext(), ExpectedOpenActivity::class.java)
                intent.putExtra("id", expectedOpen[position].id)
                startActivity(intent)
            }
        }

        rvNearCar.layoutManager = GridLayoutManager(requireContext(), 2)
        // 수평 정렬
        rvRecommendCar.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        rvExpectedOpen.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        return view
    }
}